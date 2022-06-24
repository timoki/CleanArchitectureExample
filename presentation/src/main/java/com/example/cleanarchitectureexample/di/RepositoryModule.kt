package com.example.cleanarchitectureexample.di

import com.example.data.db.dao.ConfigDataDao
import com.example.data.db.database.ConfigDatabase
import com.example.data.repository.ConfigRepositoryImpl
import com.example.data.repository.datasource.ConfigLocalDataSource
import com.example.data.repository.datasource.ConfigRemoteDataSource
import com.example.domain.repository.ConfigRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideConfigRepository(
        remoteDataSource: ConfigRemoteDataSource,
        localDataSource: ConfigLocalDataSource
    ): ConfigRepository = ConfigRepositoryImpl(remoteDataSource, localDataSource)
}