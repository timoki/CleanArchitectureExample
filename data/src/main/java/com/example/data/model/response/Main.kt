package com.example.data.model.response

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("img")
    val img: String,
    @SerializedName("url")
    val url: String
)