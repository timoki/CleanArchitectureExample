package com.example.cleanarchitectureexample.view.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.base.Result
import com.example.domain.model.config.ConfigDataModel
import com.example.domain.usecase.config.DeleteConfigLocalUseCase
import com.example.domain.usecase.config.GetConfigUseCase
import com.example.domain.usecase.config.InsertConfigLocalUseCase
import com.example.domain.usecase.member.RequestMemberLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getConfigUseCase: GetConfigUseCase,
    private val insertConfigLocalUseCase: InsertConfigLocalUseCase,
    private val deleteConfigLocalUseCase: DeleteConfigLocalUseCase,
    private val logoutUseCase: RequestMemberLogoutUseCase
): ViewModel() {

    private val _isLogin = MutableStateFlow(false)
    val isLogin
        get() = _isLogin

    private val _configDataModel = MutableStateFlow<ConfigDataModel?>(null)
    val configDataModel: StateFlow<ConfigDataModel?>
        get() = _configDataModel

    private val _networkError = Channel<String>(Channel.CONFLATED)
    val networkError = _networkError.receiveAsFlow()

    private val _isShowProgress = MutableStateFlow(false)
    val isShowProgress: StateFlow<Boolean>
        get() = _isShowProgress

    private val _loginClickChannel = Channel<Unit>(Channel.CONFLATED)
    val loginClickChannel = _loginClickChannel.receiveAsFlow()

    private fun requestGetConfig() = getConfigUseCase()
        .stateIn( // Flow 를 StateFlow 로 변환하는 작업
            scope = viewModelScope, //CoroutineScope 명시
            started = SharingStarted.WhileSubscribed(5_000), //Flow 로부터 언제부터 구독을 할지 명시(SharingStarted : Collector 가 등록되면 바로 시작, WhileSubscribed : Collector 가 없어지면 5초 후 중지)
            initialValue = Result.Loading()
        )

    fun getConfig() = viewModelScope.launch {
        requestGetConfig().collectLatest { type ->
            when(type) {
                is Result.Loading -> {
                    _isShowProgress.value = true
                }

                is Result.Success -> {
                    _isShowProgress.value = false
                    if (type.data?.result == true) {
                        Log.d(TAG, "${type.data}")
                        _configDataModel.value = type.data
                    }
                }

                is Result.NetworkError -> {
                    _isShowProgress.value = false
                    type.message?.let {
                        Log.e(TAG, it)
                        _networkError.send(it)
                    }
                }

                is Result.Error -> {
                    _isShowProgress.value = false
                    type.message?.let {
                        Log.e(TAG, it)
                        _networkError.send(it)
                    }
                }
            }
        }
    }

    fun loginClick()  = viewModelScope.launch {
        _loginClickChannel.send(Unit)
    }

    private fun requestLogout() = logoutUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Result.Loading()
        )

    fun logoutClick()  = viewModelScope.launch {
        requestLogout().collectLatest { type ->
            when(type) {
                is Result.Loading -> {
                    _isShowProgress.value = true
                }

                is Result.Success -> {
                    _isShowProgress.value = false
                    if (type.data?.result == true) {
                        _isLogin.value = false
                    }
                }

                is Result.NetworkError -> {
                    _isShowProgress.value = false
                    type.message?.let {
                        Log.e(TAG, it)
                        _networkError.send(it)
                    }
                }

                is Result.Error -> {
                    _isShowProgress.value = false
                    type.message?.let {
                        Log.e(TAG, it)
                        _networkError.send(it)
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}