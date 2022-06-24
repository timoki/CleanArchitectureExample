package com.example.domain.usecase.config

import com.example.domain.model.config.ConfigDataModel
import com.example.domain.repository.ConfigRepository

/**
 * UseCase 는 Repository 와 다르게 기능 하나하나 단위 Class 를 만든다.
 * 이렇게 만든 Class 는 DI 패턴을 사용하여 이 기능이 필요한 ViewModel 에서 받아서 사용하면 된다.
 * 이 클래스의 기능 : 클래스명 그대로 이해하면 된다 Local(Room) 에 저장된 ConfigData 를 Delete 함
 * */
class DeleteConfigLocalUseCase(
    private val configRepository: ConfigRepository
) {

    suspend operator fun invoke(configData: ConfigDataModel) {
        return configRepository.deleteConfigDataLocal(configData)
    }
}