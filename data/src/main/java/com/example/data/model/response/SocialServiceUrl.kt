package com.example.data.model.response

import com.google.gson.annotations.SerializedName

data class SocialServiceUrl(
    @SerializedName("facebook")
    val facebook: String,
    @SerializedName("instagram")
    val instagram: String,
    @SerializedName("naverBlog")
    val naverBlog: String,
    @SerializedName("naverTV")
    val naverTV: String,
    @SerializedName("tiktok")
    val tiktok: String,
    @SerializedName("twitter")
    val twitter: String,
    @SerializedName("youtube")
    val youtube: String
)