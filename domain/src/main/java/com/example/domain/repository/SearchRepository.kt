package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.live.LiveListModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getLiveData(
        limit: Int,
        searchText: String
    ): Flow<PagingData<LiveListModel>>
}