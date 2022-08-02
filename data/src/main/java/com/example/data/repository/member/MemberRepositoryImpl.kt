package com.example.data.repository.member

import com.example.data.mapper.ObjectMapper.toDefaultDataModel
import com.example.data.mapper.ObjectMapper.toLoginDataModel
import com.example.data.repository.datasource.RemoteDataSource
import com.example.domain.model.base.Result
import com.example.domain.model.defaultData.DefaultDataModel
import com.example.domain.model.login.LoginDataModel
import com.example.domain.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MemberRepository {

    override fun memberCheckId(id: String): Flow<Result<DefaultDataModel>> =
        flow<Result<DefaultDataModel>> {
            val response = remoteDataSource.memberCheckId(id)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it.toDefaultDataModel()))
                } ?: kotlin.run {
                    emit(Result.Error("예상하지 못한 오류가 발생하였습니다."))
                }
            } else {
                emit(Result.NetworkError("네트워크 요청에 실패하셨습니다. 잠시 후 다시 시도해 주세요."))
            }
        }.catch { e ->
            emit(Result.Error(e.message))
        }.flowOn(Dispatchers.IO)

    override fun memberCheckNick(nick: String): Flow<Result<DefaultDataModel>> =
        flow<Result<DefaultDataModel>> {
            val response =
                remoteDataSource.memberCheckNick(nick)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it.toDefaultDataModel()))
                } ?: kotlin.run {
                    emit(Result.Error("예상하지 못한 오류가 발생하였습니다."))
                }
            } else {
                emit(Result.NetworkError("네트워크 요청에 실패하셨습니다. 잠시 후 다시 시도해 주세요."))
            }
        }.catch { e ->
            emit(Result.Error(e.message))
        }.flowOn(Dispatchers.IO)

    override fun memberLogin(
        id: String,
        pw: String
    ): Flow<Result<LoginDataModel>> =
        flow<Result<LoginDataModel>> {
            val response = remoteDataSource.memberLogin(id, pw)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it.toLoginDataModel()))
                } ?: kotlin.run {
                    emit(Result.Error("예상하지 못한 오류가 발생하였습니다."))
                }
            } else {
                emit(Result.NetworkError("네트워크 요청에 실패하셨습니다. 잠시 후 다시 시도해 주세요."))
            }
        }.catch { e ->
            emit(Result.Error(e.message))
        }.flowOn(Dispatchers.IO)

    override fun memberJoin(
        id: String,
        nick: String,
        pw: String,
        pwRe: String,
        agreeSmsYN: String
    ): Flow<Result<DefaultDataModel>> =
        flow<Result<DefaultDataModel>> {
            val response = remoteDataSource.memberJoin(id, nick, pw, pwRe, agreeSmsYN)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it.toDefaultDataModel()))
                } ?: kotlin.run {
                    emit(Result.Error("예상하지 못한 오류가 발생하였습니다."))
                }
            } else {
                emit(Result.NetworkError("네트워크 요청에 실패하셨습니다. 잠시 후 다시 시도해 주세요."))
            }
        }.catch { e ->
            emit(Result.Error(e.message))
        }.flowOn(Dispatchers.IO)

    override fun memberLogout(): Flow<Result<DefaultDataModel>> =
        flow<Result<DefaultDataModel>> {
            val response = remoteDataSource.memberLogout()

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it.toDefaultDataModel()))
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
        private const val TAG = "UserRepositoryImpl"
    }
}