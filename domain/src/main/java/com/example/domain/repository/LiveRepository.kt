package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.live.LiveFilterType
import com.example.domain.model.live.LiveListModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface LiveRepository {
    fun getLiveData(
        limit: Int,
        sorting: StateFlow<LiveFilterType.Sorting>,
        adultType: StateFlow<LiveFilterType.AdultFilter>
    ): Flow<PagingData<LiveListModel>>
}