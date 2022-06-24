package com.example.domain.repository

import com.example.domain.model.base.Result
import com.example.domain.model.config.ConfigDataModel
import kotlinx.coroutines.flow.Flow

/**
 * Data Layer(Module)의 Config API(Remote) 및 Local Database 에게 요청할 함수를 정리하는 공간임.
 * 해당 Interface 는 Config 와 관련된 작업만 정리하며 만약 다른 API(혹은 Local) 작업이면 새로 Interface 를 만든다.(Ex. 로그인 관련 Repository -> LoginRepository)
 * 이 Interface 는 Data Layer(Module)에서 ConfigRepositoryImpl Class 를 만들어 구현하게된다.
 * */
interface ConfigRepository {
    fun getConfigData(): Flow<Result<ConfigDataModel>>

    suspend fun insertConfigDataLocal(configData: ConfigDataModel)

    suspend fun deleteConfigDataLocal(configData: ConfigDataModel)
}