package com.example.data.repository.datasource

import com.example.data.model.config.ConfigData
import com.example.data.model.defaultData.DefaultData
import com.example.data.model.login.LoginData
import retrofit2.Response

/**
 * DataSource 의 역할은 View 에서 요청이 올 경우 Repository 에서 Local 인지 Remote 인지 파악 후 각 상황에 맞는 DataSource 로 분리하여 보낼수 있게 분기점을 생성하는 것이다.
 * Remote DataSource 클래스로 요청이 들어 오면 ConfigService 로 API 를 요청한다.
 * Local DataSource 클래스로 요청이 들어 오면 ConfigDataDao 로 Room 을 요청한다.
 * */
interface RemoteDataSource {
    /** Config */
    suspend fun getConfigRemote(): Response<ConfigData>

    /** Member */
    suspend fun memberCheckId(
        id: String
    ): Response<DefaultData>

    suspend fun memberCheckNick(
        nick: String
    ): Response<DefaultData>

    suspend fun memberLogin(
        id: String,
        pw: String
    ): Response<LoginData>

    suspend fun memberJoin(
        id: String,
        nick: String,
        pw: String,
        pwRe: String,
        agreeSmsYN: String
    ): Response<DefaultData>

    suspend fun memberLogout(): Response<DefaultData>
}