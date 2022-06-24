package com.example.data.model.response

import com.google.gson.annotations.SerializedName

/**
 * Data Layer(Module)의 Model Response 는 Remote Data Class 라고 보면 된다.
 * */
data class ConfigData(
    @SerializedName("idx")
    val idx: Int, // ok
    @SerializedName("adultCheck")
    val adultCheck: AdultCheck, // ok
    @SerializedName("banner")
    val banner: Banner, // ok
    @SerializedName("broadcast")
    val broadcast: Boolean, // ok
    @SerializedName("categoryNew")
    val categoryNew: List<CategoryNew>, // ok
    @SerializedName("chatMessage")
    val chatMessage: ChatMessage, // ok
    @SerializedName("debug")
    val debug: Debug,
    @SerializedName("ivsAutoMaxQuality")
    val ivsAutoMaxQuality: Int, // ok
    @SerializedName("ivsStartQuality")
    val ivsStartQuality: Int, // ok
    @SerializedName("link")
    val link: Link, // ok
    @SerializedName("message")
    val message: String,
    @SerializedName("newChat")
    val newChat: NewChat,
    @SerializedName("result")
    val result: Boolean,
    @SerializedName("server")
    val server: Server, // ok
    @SerializedName("socialLogin")
    val socialLogin: SocialLogin, // ok
    @SerializedName("socialServiceUrl")
    val socialServiceUrl: SocialServiceUrl,
    @SerializedName("update")
    val update: List<Any>,
    @SerializedName("userIp")
    val userIp: String
)

