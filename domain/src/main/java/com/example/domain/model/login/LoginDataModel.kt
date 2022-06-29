package com.example.domain.model.login

import com.example.domain.model.defaultData.ErrorDataModel

data class LoginDataModel(
    val result: Boolean,
    val userInfo: List<Any>,
    val loginInfo: List<Any>,
    val media: List<Any>,
    val alert: List<Any>,
    val needPwChange: Boolean,
    val message: String,
    val errorData: ErrorDataModel
)
