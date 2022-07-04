package com.example.domain.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.model.base.Result
import com.example.domain.model.live.LiveListModel
import com.example.domain.repository.LiveRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LivePagingSource @Inject constructor(
    private val repository: LiveRepository,
    private val limit: Int,
    private val orderBy: String
) : PagingSource<Int, LiveListModel>() {

    override fun getRefreshKey(state: PagingState<Int, LiveListModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveListModel> {
        val page = params.key ?: 0
        var result: LoadResult<Int, LiveListModel> = LoadResult.Error(Throwable())

        repository.getLiveData(page * limit, limit, orderBy).collect { type ->
            when (type) {
                is Result.Success -> {
                    type.data?.let {
                        result = LoadResult.Page(
                            data = it,
                            prevKey = if (page == 0) null else page - 1,
                            nextKey = if (it.isEmpty()) null else page + 1
                        )
                    } ?: kotlin.run {
                        result = LoadResult.Error(Throwable(type.message))
                    }
                }

                is Result.NetworkError -> {
                    result = LoadResult.Error(Throwable(type.message))
                }

                is Result.Error -> {
                    result = LoadResult.Error(Throwable(type.message))
                }
                else -> result = LoadResult.Error(Throwable())
            }
        }

        return result
    }
}