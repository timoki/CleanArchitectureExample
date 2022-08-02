package com.example.data.model.live

import com.example.data.model.base.PageData
import com.google.gson.annotations.SerializedName

data class LiveResult(
    @SerializedName("result")
    val result: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("list")
    val list: List<LiveList>,
    @SerializedName("page")
    val page: PageData,
)