package com.example.data.model.login

import com.google.gson.annotations.SerializedName

data class DeviceInfo(
    @SerializedName("type")
    val type: String,
    @SerializedName("version")
    val version: String
)