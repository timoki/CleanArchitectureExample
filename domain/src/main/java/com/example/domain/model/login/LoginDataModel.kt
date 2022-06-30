package com.example.domain.model.login

import com.example.domain.model.defaultData.ErrorDataModel

data class LoginDataModel(
    val loginInfo: LoginInfoModel?,
    val message: String,
    val needPwChange: Boolean?,
    val result: Boolean,
    val userIp: String?,
    val errorData: ErrorDataModel?
)
