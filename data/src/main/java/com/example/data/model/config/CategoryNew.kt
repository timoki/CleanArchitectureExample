package com.example.data.model.config

import com.google.gson.annotations.SerializedName

data class CategoryNew(
    @SerializedName("code")
    val code: String,
    @SerializedName("idx")
    val idx: Int,
    @SerializedName("isList")
    val isList: Boolean,
    @SerializedName("isView")
    val isView: Boolean,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)