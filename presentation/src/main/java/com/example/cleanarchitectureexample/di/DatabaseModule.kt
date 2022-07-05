package com.example.cleanarchitectureexample.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.dao.ConfigDataDao
import com.example.data.db.dao.live.LiveDataDao
import com.example.data.db.dao.live.LiveRemoteKeysDao
import com.example.data.db.database.ConfigDatabase
import com.example.data.db.database.LiveDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    /**
     *  Context 가 필요하다면 이렇게 해주면 의존성이 주입된다.
     * */
    @Singleton
    @Provides
    fun provideConfigDatabase(
        @ApplicationContext context: Context
    ): ConfigDatabase =
        Room.databaseBuilder(
            context,
            ConfigDatabase::class.java,
            "configData.db"
        ).fallbackToDestructiveMigration()
            .enableMultiInstanceInvalidation() // 프로세스 공유
            .build()

    @Singleton
    @Provides
    fun provideLiveDatabase(
        @ApplicationContext context: Context
    ): LiveDatabase =
        Room.databaseBuilder(
            context,
            LiveDatabase::class.java,
            "liveData.db"
        ).fallbackToDestructiveMigration()
            .enableMultiInstanceInvalidation() // 프로세스 공유
            .build()

    @Singleton
    @Provides
    fun provideConfigDao(
        db: ConfigDatabase
    ): ConfigDataDao = db.configDataDao()

    @Singleton
    @Provides
    fun provideLiveDao(
        db: LiveDatabase
    ): LiveDataDao = db.liveDataDao()

    @Singleton
    @Provides
    fun provideLiveRemoteKeysDao(
        db: LiveDatabase
    ): LiveRemoteKeysDao = db.liveRemoteKeysDao()
}