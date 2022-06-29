package com.example.domain.usecase.member

import com.example.domain.model.base.Result
import com.example.domain.model.login.LoginDataModel
import com.example.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestMemberLoginUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    operator fun invoke(
        id: String,
        pw: String
    ): Flow<Result<LoginDataModel>> {
        return memberRepository.memberLogin(id, pw)
    }
}