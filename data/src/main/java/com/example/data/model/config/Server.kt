package com.example.data.model.config

import com.google.gson.annotations.SerializedName

data class Server(
    @SerializedName("api")
    val api: String,
    @SerializedName("node")
    val node: List<String>
)