package com.example.data.model.response

import com.google.gson.annotations.SerializedName

data class Win(
    @SerializedName("main")
    val main: List<Main>,
    @SerializedName("newMain")
    val newMain: List<Main>,
    @SerializedName("subLeft")
    val subLeft: SubLeftAndRight,
    @SerializedName("subRight")
    val subRight: SubLeftAndRight
)