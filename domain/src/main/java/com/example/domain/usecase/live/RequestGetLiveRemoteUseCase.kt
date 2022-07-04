package com.example.domain.usecase.live

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.domain.model.base.Result
import com.example.domain.model.config.ConfigDataModel
import com.example.domain.model.live.LiveListModel
import com.example.domain.pagingSource.LivePagingSource
import com.example.domain.repository.LiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestGetLiveRemoteUseCase @Inject constructor(
    private val repository: LiveRepository
) {
    operator fun invoke(
        limit: Int,
        orderBy: String
    ): Flow<PagingData<LiveListModel>> {
        return Pager(
            config = PagingConfig(pageSize = limit),
            pagingSourceFactory = { LivePagingSource(repository, limit, orderBy) }
        ).flow
    }
}