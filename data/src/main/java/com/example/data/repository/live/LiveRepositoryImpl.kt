package com.example.data.repository.live

import androidx.paging.*
import com.example.data.db.database.DataStoreModule
import com.example.data.db.database.LiveDatabase
import com.example.data.mapper.ObjectMapper.toModel
import com.example.data.repository.datasource.LiveRemoteMediator
import com.example.data.repository.datasource.LocalDataSource
import com.example.data.repository.datasource.RemoteDataSource
import com.example.domain.model.live.LiveListModel
import com.example.domain.repository.LiveRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LiveRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val database: LiveDatabase,
    private val dataStore: DataStoreModule
) : LiveRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getLiveData(): Flow<PagingData<LiveListModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false, prefetchDistance = 2, initialLoadSize = 1),
            remoteMediator = LiveRemoteMediator(
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource,
                database = database,
                dataStore = dataStore
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