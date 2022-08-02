package com.example.domain.repository

import com.example.domain.model.base.Result
import com.example.domain.model.defaultData.DefaultDataModel
import com.example.domain.model.login.LoginDataModel
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    fun memberCheckId(id: String): Flow<Result<DefaultDataModel>>

    fun memberCheckNick(nick: String): Flow<Result<DefaultDataModel>>

    fun memberLogin(
        id: String,
        pw: String
    ): Flow<Result<LoginDataModel>>

    fun memberJoin(
        id: String,
        nick: String,
        pw: String,
        pwRe: String,
        agreeSmsYN: String
    ): Flow<Result<DefaultDataModel>>

    fun memberLogout(): Flow<Result<DefaultDataModel>>
}