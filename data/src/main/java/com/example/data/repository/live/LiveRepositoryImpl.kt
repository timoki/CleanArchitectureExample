package com.example.data.repository.live

import androidx.paging.*
import com.example.data.db.database.LiveDatabase
import com.example.data.mapper.ObjectMapper.toModel
import com.example.data.repository.datasource.LiveRemoteMediator
import com.example.data.repository.datasource.LocalDataSource
import com.example.data.repository.datasource.RemoteDataSource
import com.example.domain.model.live.LiveFilterType
import com.example.domain.model.live.LiveListModel
import com.example.domain.repository.LiveRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LiveRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val database: LiveDatabase
) : LiveRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getLiveData(
        limit: Int,
        sorting: StateFlow<LiveFilterType.Sorting>,
        adultType: StateFlow<LiveFilterType.AdultFilter>
    ): Flow<PagingData<LiveListModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = limit,
                /*enablePlaceholders = false,*/ // 이걸 넣게되면 null 인 아이템을 허용하지 않지만 그로인해 스크롤하여 데이터를 추가로 불러오거나 refresh 할때 아이템 위치가 꼬여버린다.
                prefetchDistance = 2,
                initialLoadSize = 1
            ),
            remoteMediator = LiveRemoteMediator(
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource,
                database = database,
                sorting = sorting,
                adultType = adultType
            )
        ) {
            localDataSource.getLiveAll()
        }.flow.map {
            it.map { data ->
                data.toModel()
            }
        }.flowOn(Dispatchers.IO)
    }
}