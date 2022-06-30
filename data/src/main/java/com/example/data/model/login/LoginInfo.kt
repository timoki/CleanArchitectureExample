package com.example.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginInfo(
    @SerializedName("deviceInfo")
    val deviceInfo: DeviceInfo?,
    @SerializedName("sessKey")
    val sessionKey: String?,
    @SerializedName("siteMode")
    val siteMode: SiteMode?,
    @SerializedName("userInfo")
    val userInfo: UserInfo?
)