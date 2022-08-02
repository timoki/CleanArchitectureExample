package com.example.data.model.config

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("android")
    val android: Android,
    @SerializedName("win")
    val win: Win
)
