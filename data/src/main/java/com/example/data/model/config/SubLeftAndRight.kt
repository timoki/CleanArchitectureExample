package com.example.data.model.config

import com.google.gson.annotations.SerializedName

data class SubLeftAndRight(
    @SerializedName("img")
    val img: String,
    @SerializedName("link")
    val link: String
)