package com.example.data.repository.datasource.impl

import com.example.data.db.dao.ConfigDataDao
import com.example.data.db.entity.config.ConfigDataEntity
import com.example.data.repository.datasource.LocalDataSource
import javax.inject.Inject

/**
 * ConfigLocalDataSource Interface 를 구현하는 클래스이다.
 * ConfigRepository 에서 ConfigLocalDataSource 로 요청이 들어오면 해당 구현 클래스에서 처리를 하게 된다.
 * */
class LocalDataSourceImpl @Inject constructor(
    private val configDataDao: ConfigDataDao
) : LocalDataSource {
    override suspend fun getConfigLocal(): ConfigDataEntity = configDataDao.getConfigDataLocal()

    override suspend fun insertConfigLocal(configData: ConfigDataEntity) =
        configDataDao.insertConfigDataLocal(configData)

    override suspend fun deleteConfigLocal(configData: ConfigDataEntity) =
        configDataDao.deleteConfigDataLocal(configData)
}