package com.example.data.api

import com.example.data.model.config.ConfigData
import com.example.data.model.defaultData.DefaultData
import com.example.data.model.live.LiveResult
import com.example.data.model.login.LoginData
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    companion object {
        private const val MEMBER = "member"
        private const val CONFIG = "config"
        private const val LIVE = "live"
    }
    /** Config */
    @GET("$CONFIG/app")
    suspend fun getConfigRemote(): Response<ConfigData>

    /** Member */
    @GET("$MEMBER/check_id")
    suspend fun memberCheckId(
        @Query("id") id: String
    ): Response<DefaultData>

    @GET("$MEMBER/check_nick")
    suspend fun memberCheckNick(
        @Query("nick") nick: String
    ): Response<DefaultData>

    @FormUrlEncoded
    @POST("$MEMBER/login")
    suspend fun memberLogin(
        @Field("id") id: String,
        @Field("pw") pw: String
    ): Response<LoginData>

    @FormUrlEncoded
    @POST("$MEMBER/join")
    suspend fun memberJoin(
        @Field("id") id: String,
        @Field("nick") nick: String,
        @Field("pw") pw: String,
        @Field("pwRe") pwRe: String,
        @Field("agreeSmsYN") agreeSmsYN: String
    ): Response<DefaultData>

    @GET("$MEMBER/logout")
    suspend fun memberLogout(): Response<DefaultData>

    /** Live */
    @GET("$LIVE/index")
    suspend fun getLiveList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("orderBy") orderBy: String,
        @Query("adultType") adultType: String
    ): Response<LiveResult>
}