package com.example.data.api

import com.example.data.model.response.ConfigData
import com.example.domain.model.base.Constants
import com.example.domain.model.config.ConfigDataModel
import retrofit2.Response
import retrofit2.http.GET

/**
 * Config 와 관련된 API 활동을 정의한다.
 * 어노테이션은 @GET @POST @PUT @DELETE 등 상황에 맞게 사용하면 됨
 * Data Class 는 Entity 로 받고 RepositoryImpl 에서 Mapper 로 Convert 하여 ConfigDataModel Class 에 저장, View 에서 사용한다.
 * */
interface ConfigService {
    @GET(Constants.configAppMethod)
    suspend fun getConfigRemote(): Response<ConfigData>
}