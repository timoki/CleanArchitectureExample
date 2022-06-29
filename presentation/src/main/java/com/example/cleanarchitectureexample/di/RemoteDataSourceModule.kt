package com.example.cleanarchitectureexample.di

import com.example.data.api.ApiService
import com.example.data.repository.datasource.RemoteDataSource
import com.example.data.repository.datasource.impl.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {
    @Singleton
    @Provides
    fun provideRemoteDataSource(
        service: ApiService
    ) : RemoteDataSource = RemoteDataSourceImpl(service)
}