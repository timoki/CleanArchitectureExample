package com.example.cleanarchitectureexample.di

import androidx.viewbinding.BuildConfig
import com.example.data.api.ConfigService
import com.example.domain.model.base.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun provideConfigService(
        retrofit: Retrofit
    ) : ConfigService = retrofit.create(ConfigService::class.java)


    @Singleton
    @Provides
    fun okHttpClient(
        interceptor: HttpLoggingInterceptor
    ) : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Singleton
    @Provides
    fun httpLoggingInterceptor() : HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
}