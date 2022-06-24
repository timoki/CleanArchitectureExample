package com.example.cleanarchitectureexample.di

import android.content.Context
import androidx.room.Room
import com.example.data.api.ConfigService
import com.example.data.db.dao.ConfigDataDao
import com.example.data.db.database.ConfigDatabase
import com.example.data.repository.datasource.ConfigLocalDataSource
import com.example.data.repository.datasource.ConfigRemoteDataSource
import com.example.data.repository.datasource.impl.ConfigLocalDataSourceImpl
import com.example.data.repository.datasource.impl.ConfigRemoteDataSourceImpl
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
    ) : ConfigDatabase =
        Room.databaseBuilder(
            context,
            ConfigDatabase::class.java,
            "configData.db"
        ).fallbackToDestructiveMigration()
            .enableMultiInstanceInvalidation()
            .build()

    @Singleton
    @Provides
    fun provideConfigDao(
        db: ConfigDatabase
    ) : ConfigDataDao = db.configDataDao()
}