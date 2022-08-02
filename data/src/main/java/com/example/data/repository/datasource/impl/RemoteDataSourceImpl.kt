package com.example.data.repository.datasource.impl

import com.example.data.api.ApiService
import com.example.data.model.config.ConfigData
import com.example.data.model.defaultData.DefaultData
import com.example.data.model.live.LiveResult
import com.example.data.model.login.LoginData
import com.example.data.repository.datasource.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val service: ApiService
) : RemoteDataSource {
    override suspend fun getConfigRemote(): Response<ConfigData> = service.getConfigRemote()

    override suspend fun memberCheckId(id: String): Response<DefaultData> {
        return service.memberCheckId(id)
    }

    override suspend fun memberCheckNick(nick: String): Response<DefaultData> {
        return service.memberCheckNick(nick)
    }

    override suspend fun memberLogin(id: String, pw: String): Response<LoginData> {
        return service.memberLogin(id, pw)
    }

    override suspend fun memberJoin(
        id: String,
        nick: String,
        pw: String,
        pwRe: String,
        agreeSmsYN: String
    ): Response<DefaultData> {
        return service.memberJoin(
            id = id,
            nick = nick,
            pw = pw,
            pwRe = pwRe,
            agreeSmsYN = agreeSmsYN
        )
    }

    override suspend fun memberLogout(): Response<DefaultData> {
        return service.memberLogout()
    }

    override suspend fun getLive(
        offset: Int,
        limit: Int,
        orderBy: String,
        adultType: String
    ): Response<LiveResult> {
        return service.getLiveList(offset, limit, orderBy, adultType)
    }

    override suspend fun getLiveSearch(
        offset: Int,
        limit: Int,
        searchText: String
    ): Response<LiveResult> {
        return service.getLiveSearchList(offset, limit, searchText)
    }
}