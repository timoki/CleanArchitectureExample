package com.example.data.repository.datasource.impl

import androidx.paging.PagingSource
import com.example.data.db.dao.ConfigDataDao
import com.example.data.db.dao.live.LiveDataDao
import com.example.data.db.dao.live.LiveRemoteKeysDao
import com.example.data.db.entity.config.ConfigDataEntity
import com.example.data.db.entity.live.LiveListEntity
import com.example.data.db.entity.live.LiveRemoteKey
import com.example.data.repository.datasource.LocalDataSource
import javax.inject.Inject
/**
 * ConfigLocalDataSource Interface 를 구현하는 클래스이다.
 * ConfigRepository 에서 ConfigLocalDataSource 로 요청이 들어오면 해당 구현 클래스에서 처리를 하게 된다.
 * */
class LocalDataSourceImpl @Inject constructor(
    private val configDataDao: ConfigDataDao,
    private val liveDataDao: LiveDataDao,
    private val liveRemoteKeysDao: LiveRemoteKeysDao
) : LocalDataSource {
    override suspend fun getConfigLocal(): ConfigDataEntity = configDataDao.getConfigDataLocal()

    override suspend fun insertConfigLocal(configData: ConfigDataEntity) =
        configDataDao.insertConfigDataLocal(configData)

    override suspend fun deleteConfigLocal(configData: ConfigDataEntity) =
        configDataDao.deleteConfigDataLocal(configData)

    override fun getLiveAll(): PagingSource<Int, LiveListEntity> =
        liveDataDao.getLiveDataAll()

    override fun getLive(idValue: Int): LiveListEntity =
        liveDataDao.getLiveDataFromId(idValue)

    override suspend fun insertLiveLocal(liveData: List<LiveListEntity>) {
        liveDataDao.insertLiveData(liveData)
    }

    override suspend fun deleteLiveDataAll() {
        liveDataDao.deleteLiveDataAll()
    }

    override suspend fun insertLiveKeys(remoteKey: LiveRemoteKey) {
        liveRemoteKeysDao.insertLiveKeys(remoteKey)
    }

    override suspend fun remoteKeysWhereKeyId(keyId: Int): LiveRemoteKey? =
        liveRemoteKeysDao.remoteKeysWhereKeyId(keyId)

    override suspend fun remoteKeysWhereLiveId(liveId: String): LiveRemoteKey? =
        liveRemoteKeysDao.remoteKeysWhereLiveId(liveId)

    override suspend fun clearRemoteKeys() {
        liveRemoteKeysDao.clearRemoteKeys()
    }
}

