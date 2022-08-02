package com.example.data.model.config

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