package com.example.cleanarchitectureexample.di

import com.example.data.api.ConfigService
import com.example.data.db.dao.ConfigDataDao
import com.example.data.repository.datasource.ConfigLocalDataSource
import com.example.data.repository.datasource.ConfigRemoteDataSource
import com.example.data.repository.datasource.impl.ConfigLocalDataSourceImpl
import com.example.data.repository.datasource.impl.ConfigRemoteDataSourceImpl
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
        configDataDao: ConfigDataDao
    ) : ConfigLocalDataSource = ConfigLocalDataSourceImpl(configDataDao)
}