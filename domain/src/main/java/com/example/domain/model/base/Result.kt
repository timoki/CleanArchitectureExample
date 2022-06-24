package com.example.domain.model.base

/**
 * Remote, Local 통신 시 진행 상태 및 Response 결과에 따라 ViewModel 에 상태를 전달할 수 있게 sealed class 로 각 상태를 정의함
 * */
sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Result<T>(data)

    class Error<T>(message: String?) : Result<T>(message = message)

    class NetworkError<T>(message: String?) : Result<T>(message = message)

    class Loading<T> : Result<T>()
}