package com.example.data.model.defaultData

import com.google.gson.annotations.SerializedName

data class DefaultData(
    @SerializedName("result")
    val result: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("errorData")
    val errorData: ErrorData?
)
