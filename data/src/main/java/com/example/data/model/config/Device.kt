package com.example.data.model.config

import com.google.gson.annotations.SerializedName

data class Device(
    @SerializedName("token")
    val token: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("version")
    val version: String
)