package com.example.data.repository.datasource

import com.example.data.db.entity.config.ConfigDataEntity
import com.example.data.db.entity.live.LiveListEntity

/**
 * DataSource 의 역할은 View 에서 요청이 올 경우 Repository 에서 Local 인지 Remote 인지 파악 후 각 상황에 맞는 DataSource 로 분리하여 보낼수 있게 분기점을 생성하는 것이다.
 * Remote DataSource 클래스로 요청이 들어 오면 ConfigService 로 API 를 요청한다.
 * Local DataSource 클래스로 요청이 들어 오면 ConfigDataDao 로 Room 을 요청한다.
 * */
interface LocalDataSource {
    suspend fun getConfigLocal(): ConfigDataEntity?

    suspend fun insertConfigLocal(configData: ConfigDataEntity)

    suspend fun deleteConfigLocal(configData: ConfigDataEntity)

    suspend fun getLiveAll(): List<LiveListEntity>

    suspend fun getLive(_id: Int): LiveListEntity?

    suspend fun insertLiveLocal(liveData: List<LiveListEntity>)
}