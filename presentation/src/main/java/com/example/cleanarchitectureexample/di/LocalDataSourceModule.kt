package com.example.cleanarchitectureexample.di

import com.example.data.db.dao.ConfigDataDao
import com.example.data.db.dao.live.LiveDataDao
import com.example.data.db.dao.live.LiveRemoteKeysDao
import com.example.data.repository.datasource.LocalDataSource
import com.example.data.repository.datasource.impl.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {
    @Singleton
    @Provides
    fun provideConfigLocalDataSource(
        configDataDao: ConfigDataDao,
        liveDataDao: LiveDataDao,
        liveRemoteKeysDao: LiveRemoteKeysDao
    ): LocalDataSource = LocalDataSourceImpl(configDataDao, liveDataDao, liveRemoteKeysDao)
}