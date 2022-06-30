package com.example.data.model.login

import com.example.data.model.defaultData.ErrorData
import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("loginInfo")
    val loginInfo: LoginInfo?,
    @SerializedName("message")
    val message: String,
    @SerializedName("needPwChange")
    val needPwChange: Boolean?,
    @SerializedName("result")
    val result: Boolean,
    @SerializedName("userIp")
    val userIp: String?,
    @SerializedName("errorData")
    val errorData: ErrorData?
)