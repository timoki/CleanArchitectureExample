package com.example.domain.usecase.live

import androidx.paging.PagingData
import com.example.domain.model.live.LiveListModel
import com.example.domain.repository.LiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestGetLiveRemoteUseCase @Inject constructor(
    private val repository: LiveRepository
) {
    operator fun invoke(): Flow<PagingData<LiveListModel>> {
        return repository.getLiveData()
    }
}