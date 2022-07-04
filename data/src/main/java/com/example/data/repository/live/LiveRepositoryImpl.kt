package com.example.data.repository.live

import com.example.data.mapper.ObjectMapper.toConfigDataEntity
import com.example.data.mapper.ObjectMapper.toConfigDataModel
import com.example.data.mapper.ObjectMapper.toLiveListEntityFromList
import com.example.data.mapper.ObjectMapper.toLiveListModelFromEntity
import com.example.data.mapper.ObjectMapper.toLiveListModelFromList
import com.example.data.repository.datasource.LocalDataSource
import com.example.data.repository.datasource.RemoteDataSource
import com.example.domain.model.base.Result
import com.example.domain.model.config.ConfigDataModel
import com.example.domain.model.live.LiveListModel
import com.example.domain.model.live.LiveResultModel
import com.example.domain.repository.ConfigRepository
import com.example.domain.repository.LiveRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LiveRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : LiveRepository {
    override fun getLiveData(
        offset: Int,
        limit: Int,
        orderBy: String
    ): Flow<Result<List<LiveListModel>>> =
        flow<Result<List<LiveListModel>>> {

            val response = remoteDataSource.getLive(offset, limit, orderBy)

            if (response.isSuccessful) {
                response.body()?.let {
                    coroutineScope {
                        localDataSource.insertLiveLocal(it.list.toLiveListEntityFromList())
                    }

                    val localResponse = localDataSource.getLiveAll()

                    /*localResponse?.let { entity ->
                        emit(Result.Success(entity.toLiveListModelFromEntity()))
                        return@flow
                    }*/

                    emit(Result.Success(it.list.toLiveListModelFromList()))
                } ?: kotlin.run {
                    emit(Result.Error("예상하지 못한 오류가 발생하였습니다."))
                }
            } else {
                emit(Result.NetworkError("네트워크 요청에 실패하셨습니다. 잠시 후 다시 시도해 주세요."))
            }
        }.catch { e ->
            emit(Result.Error(e.message))
        }.flowOn(Dispatchers.IO)

    companion object {
        private const val TAG = "LiveRepositoryImpl"
    }
}