package com.example.data.repository.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.data.db.database.LiveDatabase
import com.example.data.db.entity.live.LiveListEntity
import com.example.data.db.entity.live.LiveRemoteKey
import com.example.data.mapper.ObjectMapper.toLiveListEntityFromList
import com.example.domain.model.live.LiveFilterType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * @author choi Young-jun
 * 앱에서 캐쉬(임시저장)된 데이터를 모두 소진한 경우 페이징 라이브러리에게 신호를 보내는 역할을 한다.
 * 네트워크 및 Local Db 에서 페이징 데이터를 로드하는 역할을 한다.
 * 로컬 DB 가 페이징 어댑터의 주요 데이터 소스이므로 페이징을 구현하는 좋은 방법 (안정적이며 오류 발생확률이 낮음)
 */
@OptIn(ExperimentalPagingApi::class)
class LiveRemoteMediator @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val database: LiveDatabase,
    private val sorting: StateFlow<LiveFilterType.Sorting>,
    private val adultType: StateFlow<LiveFilterType.AdultFilter>
) : RemoteMediator<Int, LiveListEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LiveListEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                /**
                 * @author seok ho Kang
                 * adapter.refresh() 를 하면 여기로 옴
                 * 여기서 nextKey 값을 현재 아이템 기준(anchorPosition)으로 가져오니 스크롤 하여 추가로 아이템을 가져온 후(page 가 0이 아니면) refresh 를 타면 무한으로 서버와 통신하게 됨.
                 * refresh 할 때 별도의 작업을 하지 않는다면(혹은 확실하게 remoteKey 를 가져오지 못한다면) 0으로 고정하는게 좋을듯
                 * */
                LoadType.REFRESH -> {
                    /*val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 0*/
                    0
                }

                /**
                 * remoteKeys 가 null 이면 새로 고침 결과가 아직 데이터베이스에 없음을 의미합니다.
                 * prevKey 를 구하여 진행하면 상단 아이템으로 스크롤 시 이전 페이지 정보를 가져옵니다.
                 */
                LoadType.PREPEND -> {
                    /*val remoteKeys = getRemoteKeyForFirstItem(state)

                    remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)*/
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                /**
                 * remoteKeys 가 null 이면 새로 고침 결과가 아직 데이터베이스에 없음을 의미합니다.
                 * RemoteKeys 가 null 이 아닌 경우 Paging 이 이 메서드를 다시 호출하기 때문에
                 * endOfPaginationReached = false 로 Success 를 반환할 수 있습니다.
                 * remoteKeys 가 null 이 아니지만 nextKey 가 null 이면 추가를 위한 페이지 매김 끝에 도달했음을 의미합니다.
                 */
                LoadType.APPEND -> {
                    /*val remoteKeys = getRemoteKeyForLastItem(state)

                    remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)*/
                    val lastItem = state.lastItemOrNull()
                    val remoteKey: LiveRemoteKey? = database.withTransaction {
                        if (lastItem?.code != null) {
                            localDataSource.remoteKeysWhereLiveId(lastItem.code)
                        } else null
                    }

                    remoteKey?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            // network
            val response = withContext(Dispatchers.IO) {
                remoteDataSource.getLive(
                    page * state.config.pageSize,
                    state.config.pageSize,
                    sorting.value.value,
                    adultType.value.value
                )
            }

            val list = response.body()?.list ?: return MediatorResult.Error(Exception())
            val endOfPaginationReached = response.body()?.page?.lastPage

            if (response.isSuccessful) {
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        localDataSource.deleteLiveDataAll()
                        localDataSource.clearRemoteKeys()
                    }


                    val prevKey = if (page == 0) null else page - 1
                    val nextKey = if (endOfPaginationReached == page) null else page + 1

                    list.forEachIndexed { index, liveList ->
                        localDataSource.insertLiveKeys(
                            LiveRemoteKey(
                                keyId = (page * 20) + index,
                                liveId = liveList.code,
                                prevKey = prevKey,
                                nextKey = nextKey
                            )
                        )
                    }

                    localDataSource.insertLiveLocal(list.toLiveListEntityFromList())
                }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == page)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    /**
     * 항목이 포함된 검색된 마지막 페이지를 가져옵니다.
     *
     * 마지막 페이지에서 마지막 항목을 가져옵니다.
     * */
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LiveListEntity>): LiveRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { live ->
                // Get the remote keys of the last item retrieved
                localDataSource.remoteKeysWhereLiveId(live.code)
            }
    }

    /**
     * 항목이 포함된 검색된 첫 번째 페이지를 가져옵니다.
     *
     * 첫 페이지에서 첫 번째 항목을 가져옵니다.
     * */
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, LiveListEntity>): LiveRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { live ->
                // Get the remote keys of the first items retrieved
                localDataSource.remoteKeysWhereLiveId(live.code)
            }
    }

    /**
     * 페이징 라이브러리가 앵커 위치 이후에 데이터를 로드하려고 합니다.
     *
     * 앵커 위치에 가장 가까운 항목을 가져옵니다.
     * */
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LiveListEntity>
    ): LiveRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.code?.let { code ->
                localDataSource.remoteKeysWhereLiveId(code)
            }
        }
    }
}