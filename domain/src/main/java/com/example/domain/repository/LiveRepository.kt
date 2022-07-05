package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.live.LiveListModel
import kotlinx.coroutines.flow.Flow

interface LiveRepository {
    fun getLiveData(
        limit: Int,
        orderBy: String
    ): Flow<PagingData<LiveListModel>>
}