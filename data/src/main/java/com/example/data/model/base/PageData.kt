package com.example.data.model.base

import com.google.gson.annotations.SerializedName

data class PageData(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("lastPage")
    val lastPage: Int
)