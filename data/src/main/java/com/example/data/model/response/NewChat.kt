package com.example.data.model.response

import com.google.gson.annotations.SerializedName

data class NewChat(
    @SerializedName("api")
    val api: String,
    @SerializedName("node")
    val node: String,
    @SerializedName("roomUserCount")
    val roomUserCount: String,
    @SerializedName("status")
    val status: String
)