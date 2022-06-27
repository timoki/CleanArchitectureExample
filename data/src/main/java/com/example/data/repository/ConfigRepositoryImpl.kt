package com.example.data.repository

import com.example.data.mapper.ObjectMapper.toConfigDataEntity
import com.example.data.mapper.ObjectMapper.toConfigDataModel
import com.example.data.repository.datasource.ConfigLocalDataSource
import com.example.data.repository.datasource.ConfigRemoteDataSource
import com.example.domain.model.base.Result
import com.example.domain.model.config.ConfigDataModel
import com.example.domain.repository.ConfigRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ConfigRepositoryImpl(
    private val remoteDataSource: ConfigRemoteDataSource,
    private val localDataSource: ConfigLocalDataSource
) : ConfigRepository {
    /**
     * 1. Entity 에 접근하면 안댐
     * Mapper 사용 (Gson(), Jackson 은 피해서 사용하셔야 합니다.
     * 인스턴스를 계속 생성하면 어플이 느려집니다.
     * 저장할때 configDataModel -> ConfigDataEntity
     * 가져올때 configDataEntity -> ConfigDataModel
     *
     * 2. Room에 저장된 데이터가 없으면 API 요청하고 Room에 저장된 데이터가 있으면 API를 호출하지 않는다.
     */
    override fun getConfigData(): Flow<Result<ConfigDataModel>> =
        flow<Result<ConfigDataModel>> {
            val localResponse = localDataSource.getConfigLocal()
            localResponse?.let {
                emit(Result.Success(it.toConfigDataModel()))
                return@flow
            }

            val response = remoteDataSource.getConfigRemote()

            if (response.isSuccessful) {
                response.body()?.let {
                    localDataSource.insertConfigLocal(it.toConfigDataEntity())
                    emit(Result.Success(it.toConfigDataModel()))
                } ?: kotlin.run {
                    emit(Result.Error("예상하지 못한 오류가 발생하였습니다."))
                }
            } else {
                emit(Result.NetworkError("네트워크 요청에 실패하셨습니다. 잠시 후 다시 시도해 주세요."))
            }
        }.catch { e ->
            emit(Result.Error(e.message))
        }.flowOn(Dispatchers.IO)

    override suspend fun insertConfigDataLocal(configData: ConfigDataModel) {
        return localDataSource.insertConfigLocal(configData.toConfigDataEntity())
    }

    override suspend fun deleteConfigDataLocal(configData: ConfigDataModel) {
        return localDataSource.deleteConfigLocal(configData.toConfigDataEntity())
    }

    companion object {
        private const val TAG = "ConfigRepositoryImpl"
    }
}