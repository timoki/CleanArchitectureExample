package com.example.domain.usecase.config

import com.example.domain.model.base.Result
import com.example.domain.model.config.ConfigDataModel
import com.example.domain.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow

/**
 * UseCase 는 Repository 와 다르게 기능 하나하나 단위 Class 를 만든다.
 * 이렇게 만든 Class 는 DI 패턴을 사용하여 이 기능이 필요한 ViewModel 에서 받아서 사용하면 된다.
 * 이 클래스의 기능 : 클래스명 그대로 이해하면 된다 Remote(API) 에서 ConfigData 를 요청하여 Get 함
 * */
class GetConfigUseCase(
    private val configRepository: ConfigRepository
) {

    operator fun invoke(): Flow<Result<ConfigDataModel>> {
        return configRepository.getConfigData()
    }
}