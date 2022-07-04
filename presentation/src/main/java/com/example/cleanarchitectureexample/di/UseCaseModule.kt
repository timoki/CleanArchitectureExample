package com.example.cleanarchitectureexample.di

import com.example.domain.repository.ConfigRepository
import com.example.domain.repository.LiveRepository
import com.example.domain.repository.MemberRepository
import com.example.domain.usecase.config.DeleteConfigLocalUseCase
import com.example.domain.usecase.config.GetConfigUseCase
import com.example.domain.usecase.config.InsertConfigLocalUseCase
import com.example.domain.usecase.live.RequestGetLiveRemoteUseCase
import com.example.domain.usecase.member.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @InstallIn 어노테이션에 ViewModelComponent 를 넣으면 ViewModel Lifecycle 을 따라간다.
 * */
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    /**
     * ViewModel 에 의존성 주입 시 @ViewModelScoped 어노테이션을 사용한다.
     * */
    @ViewModelScoped
    @Provides
    fun provideGetConfigRemoteUseCase(
        configRepository: ConfigRepository
    ): GetConfigUseCase = GetConfigUseCase(configRepository)

    @ViewModelScoped
    @Provides
    fun provideInsertConfigLocalUseCase(
        configRepository: ConfigRepository
    ): InsertConfigLocalUseCase = InsertConfigLocalUseCase(configRepository)

    @ViewModelScoped
    @Provides
    fun provideDeleteConfigLocalUseCase(
        configRepository: ConfigRepository
    ): DeleteConfigLocalUseCase = DeleteConfigLocalUseCase(configRepository)

    @ViewModelScoped
    @Provides
    fun provideRequestMemberCheckIdUseCase(
        memberRepository: MemberRepository
    ): RequestMemberCheckIdUseCase = RequestMemberCheckIdUseCase(memberRepository)

    @ViewModelScoped
    @Provides
    fun provideRequestMemberCheckNickUseCase(
        memberRepository: MemberRepository
    ): RequestMemberCheckNickUseCase = RequestMemberCheckNickUseCase(memberRepository)

    @ViewModelScoped
    @Provides
    fun provideRequestMemberLoginUseCase(
        memberRepository: MemberRepository
    ): RequestMemberLoginUseCase = RequestMemberLoginUseCase(memberRepository)

    @ViewModelScoped
    @Provides
    fun provideRequestMemberJoinUseCase(
        memberRepository: MemberRepository
    ): RequestMemberJoinUseCase = RequestMemberJoinUseCase(memberRepository)

    @ViewModelScoped
    @Provides
    fun provideRequestMemberLogoutUseCase(
        memberRepository: MemberRepository
    ): RequestMemberLogoutUseCase = RequestMemberLogoutUseCase(memberRepository)

    @ViewModelScoped
    @Provides
    fun provideRequestGetLiveRemoteUseCase(
        liveRepository: LiveRepository
    ): RequestGetLiveRemoteUseCase = RequestGetLiveRemoteUseCase(liveRepository)
}