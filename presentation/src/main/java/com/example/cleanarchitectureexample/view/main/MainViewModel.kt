package com.example.cleanarchitectureexample.view.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.utils.ResourceProvider
import com.example.data.db.database.DataStoreModule
import com.example.domain.model.base.Result
import com.example.domain.model.config.ConfigDataModel
import com.example.domain.model.login.LoginDataModel
import com.example.domain.usecase.config.GetConfigUseCase
import com.example.domain.usecase.member.RequestMemberLoginUseCase
import com.example.domain.usecase.member.RequestMemberLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getConfigUseCase: GetConfigUseCase,
    private val logoutUseCase: RequestMemberLogoutUseCase,
    private val requestMemberLoginUseCase: RequestMemberLoginUseCase,
    private val dataStore: DataStoreModule,
    private val resource: ResourceProvider
): ViewModel() {

    val isLogin = MutableStateFlow<LoginDataModel?>(null)

    val configApp = MutableStateFlow<ConfigDataModel?>(null)

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

    fun test() {
        
    }

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

                        configApp.value = type.data
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
                        isLogin.value = null
                        if (!dataStore.isMemoryId().first()) {
                            dataStore.saveId("")
                        }

                        dataStore.savePw("")
                        dataStore.saveAutoLogin(false)
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

    fun login() = viewModelScope.launch {
        val id = dataStore.getSaveId().first()
        val pw = dataStore.getSaveId().first()
        if (id.isNotEmpty() && pw.isNotEmpty()) {
            requestLogin(id, pw).collect { type ->
                when (type) {
                    is Result.Loading -> {
                        _isShowProgress.value = true
                    }

                    is Result.Success -> {
                        _isShowProgress.value = false

                        if (type.data?.result == true) {
                            isLogin.value = type.data
                        } else {
                            _networkError.send(resource.getString(R.string.warning_auto_login))
                        }
                    }

                    is Result.NetworkError -> {
                        _isShowProgress.value = false
                        type.message?.let {
                            _networkError.send(it)
                        }
                    }

                    is Result.Error -> {
                        _isShowProgress.value = false
                        type.message?.let {
                            _networkError.send(it)
                        }
                    }
                }
            }
        }
    }

    //로그인 처리
    private fun requestLogin(id: String, pw: String) =
        requestMemberLoginUseCase(id, pw)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Result.Loading()
            )

    companion object {
        private const val TAG = "MainViewModel"
    }
}