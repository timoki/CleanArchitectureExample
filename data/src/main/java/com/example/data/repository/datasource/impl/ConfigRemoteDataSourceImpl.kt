package com.example.data.repository.datasource.impl

import com.example.data.api.ConfigService
import com.example.data.model.response.ConfigData
import com.example.data.repository.datasource.ConfigRemoteDataSource
import retrofit2.Response

/**
 * ConfigRemoteDataSource Interface 를 구현하는 클래스이다.
 * ConfigRepository 에서 ConfigRemoteDataSource 로 요청이 들어오면 해당 구현 클래스에서 처리를 하게 된다.
 * */
class ConfigRemoteDataSourceImpl(
    private val service: ConfigService
) : ConfigRemoteDataSource {
    override suspend fun getConfigRemote(): Response<ConfigData> = service.getConfigRemote()
}