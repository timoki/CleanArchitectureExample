package com.example.cleanarchitectureexample.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.example.data.api.ApiService
import com.example.domain.model.base.Constants
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger Hilt DI
 * @Module, @InstallIn 어노테이션을 추가함으로써 Hilt 사용이 선언된다.
 * @InstallIn(@param) 파라메터를 SingletonComponent 으로 설정하면 Application Lifecycle 을 따라감
 * Hilt 는 싱글톤으로 사용되어야 하기 때문에 object 로 생성한다.
 * 함수를 만들 때 어노테이션이 삽입되어야 함
 * 객체를 만들고 다른 @Provides 함수에서 매개변수로 받으면 의존성이 주입된다.
 * 코드 줄 숫자쪽에 마크가 표시되면 정상적으로 주입이 되고 마크 클릭 시 대상이 뭔지 보여준다.
 * */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideRetrofitInstance(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun okHttpClient(
        @ApplicationContext context: Context,
        interceptor: HttpLoggingInterceptor,
        headerInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .cookieJar(
            PersistentCookieJar(
                SetCookieCache(), SharedPrefsCookiePersistor(context)
            )
        )
        .addInterceptor(interceptor)
        .addInterceptor(headerInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    @Singleton
    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Singleton
    @Provides
    fun provideHeaderInterceptor(): Interceptor = // 헤더는 원래 어플의 설정값을 입력해야 하지만 일단 테스트를 위해 고정값으로 설정
        Interceptor {
            it.proceed(
                it.request()
                    .newBuilder()
                    .addHeader("t", "android")
                    .addHeader("v", "3.1.10")
                    .addHeader("p", "com.example.cleanarchitectureexample")
                    .addHeader("i", "blAyc0RqUE1ZMW9vNGFxWmdwRWpmeGhOQWZwci95N1hqVnNTbkJZVUl2UQo")
                    .addHeader("s", "playstore")
                    .build()
            )
        }
}