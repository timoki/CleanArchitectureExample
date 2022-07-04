package com.example.cleanarchitectureexample.di

import com.example.data.repository.config.ConfigRepositoryImpl
import com.example.data.repository.datasource.LocalDataSource
import com.example.data.repository.member.MemberRepositoryImpl
import com.example.data.repository.datasource.RemoteDataSource
import com.example.data.repository.live.LiveRepositoryImpl
import com.example.domain.repository.ConfigRepository
import com.example.domain.repository.LiveRepository
import com.example.domain.repository.MemberRepository
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
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): ConfigRepository = ConfigRepositoryImpl(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideMemberRepository(
        remoteDataSource: RemoteDataSource
    ): MemberRepository = MemberRepositoryImpl(remoteDataSource)

    @Singleton
    @Provides
    fun provideLiveRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): LiveRepository = LiveRepositoryImpl(remoteDataSource, localDataSource)
}