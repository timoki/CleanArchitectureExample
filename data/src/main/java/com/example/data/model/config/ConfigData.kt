package com.example.data.model.config

import com.google.gson.annotations.SerializedName

/**
 * Data Layer(Module)의 Model Response 는 Remote Data Class 라고 보면 된다.
 * */
data class ConfigData(
    @SerializedName("appMutex")
    val appMutex: Boolean,
    @SerializedName("adultCheck")
    val adultCheck: AdultCheck,
    @SerializedName("banner")
    val banner: Banner,
    @SerializedName("broadcast")
    val broadcast: Boolean,
    @SerializedName("categoryNew")
    val categoryNew: List<CategoryNew>,
    @SerializedName("chatMessage")
    val chatMessage: ChatMessage,
    @SerializedName("debug")
    val debug: Debug,
    @SerializedName("ivsAutoMaxQuality")
    val ivsAutoMaxQuality: Int,
    @SerializedName("ivsStartQuality")
    val ivsStartQuality: Int,
    @SerializedName("link")
    val link: Link,
    @SerializedName("message")
    val message: String,
    @SerializedName("newChat")
    val newChat: NewChat,
    @SerializedName("result")
    val result: Boolean,
    @SerializedName("server")
    val server: Server,
    @SerializedName("socialLogin")
    val socialLogin: Map<String, Boolean>,
    @SerializedName("socialServiceUrl")
    val socialServiceUrl: SocialServiceUrl,
    @SerializedName("update")
    val update: List<Any>
)

