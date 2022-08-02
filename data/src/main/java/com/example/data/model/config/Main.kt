package com.example.data.model.config

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("img")
    val img: String,
    @SerializedName("url")
    val url: String
)