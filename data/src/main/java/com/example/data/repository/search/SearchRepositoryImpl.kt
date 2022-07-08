package com.example.data.repository.search

import androidx.paging.*
import com.example.data.mapper.ObjectMapper.toLiveListModel
import com.example.data.repository.datasource.RemoteDataSource
import com.example.data.repository.datasource.SearchPagingDataSource
import com.example.domain.model.live.LiveListModel
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {
    override fun getLiveData(
        limit: Int,
        searchText: String
    ): Flow<PagingData<LiveListModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = limit,
                prefetchDistance = 2,
                initialLoadSize = 1
            ),
            pagingSourceFactory = {
                SearchPagingDataSource(
                    remoteDataSource = remoteDataSource,
                    searchText = searchText
                )
            }
        ).flow.map {
            it.map { data ->
                data.toLiveListModel()
            }
        }.flowOn(Dispatchers.IO)
    }
}