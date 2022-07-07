package com.example.domain.usecase.live

import androidx.paging.PagingData
import com.example.domain.model.live.LiveFilterType
import com.example.domain.model.live.LiveListModel
import com.example.domain.repository.LiveRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RequestGetLiveRemoteUseCase @Inject constructor(
    private val repository: LiveRepository
) {
    operator fun invoke(
        limit: Int,
        sorting: StateFlow<LiveFilterType.Sorting>,
        adultType: StateFlow<LiveFilterType.AdultFilter>
    ): Flow<PagingData<LiveListModel>> {
        return repository.getLiveData(limit, sorting, adultType)
    }
}