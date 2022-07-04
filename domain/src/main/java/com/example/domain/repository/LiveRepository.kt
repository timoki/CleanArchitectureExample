package com.example.domain.repository

import com.example.domain.model.base.Result
import com.example.domain.model.live.LiveListModel
import kotlinx.coroutines.flow.Flow

interface LiveRepository {
    fun getLiveData(
        offset: Int,
        limit: Int,
        orderBy: String
    ): Flow<Result<List<LiveListModel>>>
}