package com.example.domain.usecase.member

import com.example.domain.model.base.Result
import com.example.domain.model.defaultData.DefaultDataModel
import com.example.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestMemberCheckNickUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    operator fun invoke(nick: String): Flow<Result<DefaultDataModel>> {
        return memberRepository.memberCheckNick(nick)
    }
}