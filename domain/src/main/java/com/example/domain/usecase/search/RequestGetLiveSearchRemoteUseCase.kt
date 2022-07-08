package com.example.domain.usecase.search

import androidx.paging.PagingData
import com.example.domain.model.live.LiveListModel
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestGetLiveSearchRemoteUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(
        limit: Int,
        searchText: String
    ): Flow<PagingData<LiveListModel>> {
        return repository.getLiveData(limit, searchText)
    }
}