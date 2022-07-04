package com.example.domain.model.live

import com.example.domain.model.base.PageDataModel

data class LiveResultModel(
    val result: Boolean,
    val message: String,
    val list: List<LiveListModel>,
    val page: PageDataModel,
    val userIp: String,
)