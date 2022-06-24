package com.example.data.model.response

import com.google.gson.annotations.SerializedName

data class ChatMessage(
    @SerializedName("intro")
    val intro: String
)