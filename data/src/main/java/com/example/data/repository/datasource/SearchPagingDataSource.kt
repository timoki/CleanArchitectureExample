package com.example.data.repository.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.model.live.LiveList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchPagingDataSource(
    private val remoteDataSource: RemoteDataSource,
    private val searchText: String
) : PagingSource<Int, LiveList>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveList> {
        val pageNumber = params.key ?: 0
        return try {
            val response = withContext(Dispatchers.IO) {
                remoteDataSource.getLiveSearch(
                    pageNumber * params.loadSize,
                    params.loadSize,
                    searchText
                )
            }

            if (response.isSuccessful) {
                response.body()?.let {
                    val page = it.page.page
                    val prevKey = if (page == 0) null else page - 1
                    val nextKey = if (it.page.lastPage == page) null else page + 1

                    LoadResult.Page(
                        data = it.list,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                } ?: run {
                    LoadResult.Error(Exception())
                }
            } else {
                LoadResult.Error(Exception())
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveList>): Int = 0
}