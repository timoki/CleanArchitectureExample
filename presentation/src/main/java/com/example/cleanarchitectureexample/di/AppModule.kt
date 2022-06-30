package com.example.cleanarchitectureexample.di

import android.content.Context
import com.example.data.db.database.DataStoreModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // android 의 application 이 생성되면 생성되고 application 이 죽으면 삭제
object AppModule {

    @Singleton
    @Provides
    fun provideDataStoreModule(
        @ApplicationContext context: Context
    ): DataStoreModule {
        return DataStoreModule(context)
    }
}