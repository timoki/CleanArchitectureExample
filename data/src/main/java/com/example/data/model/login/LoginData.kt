package com.example.data.model.login

import com.example.data.model.defaultData.ErrorData
import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("result")
    val result: Boolean,
    @SerializedName("userInfo")
    val userInfo: List<Any>,
    @SerializedName("loginInfo")
    val loginInfo: List<Any>,
    @SerializedName("media")
    val media: List<Any>,
    @SerializedName("alert")
    val alert: List<Any>,
    @SerializedName("needPwChange")
    val needPwChange: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("errorData")
    val errorData: ErrorData
)
