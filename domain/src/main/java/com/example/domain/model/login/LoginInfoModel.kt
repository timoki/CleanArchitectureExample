package com.example.domain.model.login

data class LoginInfoModel(
    val deviceInfo: DeviceInfoModel,
    val sessionKey: String,
    val siteMode: SiteModeModel,
    val userInfo: UserInfoModel
)