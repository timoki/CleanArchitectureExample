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
                    0
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
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
}