package com.example.data.model.config

import com.google.gson.annotations.SerializedName

data class AdultCheck(
    @SerializedName("chatMessage")
    val chatMessage: Boolean?,
    @SerializedName("post")
    val post: Boolean,
    @SerializedName("recom")
    val recom: Boolean
)
