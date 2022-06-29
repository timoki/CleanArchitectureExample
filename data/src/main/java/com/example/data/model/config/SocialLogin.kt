package com.example.data.model.config

import com.google.gson.annotations.SerializedName

data class SocialLogin(
    @SerializedName("FACEBOOK")
    val FACEBOOK: Boolean,
    @SerializedName("GOOGLE")
    val GOOGLE: Boolean,
    @SerializedName("KAKAO")
    val KAKAO: Boolean,
    @SerializedName("NAVER")
    val NAVER: Boolean
)