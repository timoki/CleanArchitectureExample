package com.example.cleanarchitectureexample.view.main

import android.widget.CompoundButton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.utils.ResourceProvider
import com.example.data.db.database.DataStoreModule
import com.example.domain.model.base.Result
import com.example.domain.model.config.ConfigDataModel
import com.example.domain.model.live.LiveFilterType
import com.example.domain.model.login.LoginDataModel
import com.example.domain.usecase.config.GetConfigUseCase
import com.example.domain.usecase.live.RequestGetLiveRemoteUseCase
import com.example.domain.usecase.member.RequestMemberLoginUseCase
import com.example.domain.usecase.member.RequestMemberLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getConfigUseCase: GetConfigUseCase,
    private val logoutUseCase: RequestMemberLogoutUseCase,
    private val requestMemberLoginUseCase: RequestMemberLoginUseCase,
    private val requestGetLiveRemoteUseCase: RequestGetLiveRemoteUseCase,
    private val dataStore: DataStoreModule,
    private val resource: ResourceProvider
) : ViewModel() {

    sealed class LoginState {
        class Ok(data: LoginDataModel) : LoginState()
        object Guest : LoginState()
    }

    enum class ViewState {
        NOT_EMPTY_ERROR, ERROR, VIEW, LOADING, EMPTY
    }

    val viewState = MutableStateFlow(ViewState.LOADING)

    val loginModel = MutableStateFlow<LoginState>(LoginState.Guest)

    val configApp = MutableStateFlow<ConfigDataModel?>(null)

    private val _networkState = Channel<Pair<Boolean, String>>(Channel.CONFLATED)
    val networkState = _networkState.receiveAsFlow()

    val isShowProgress = MutableStateFlow(false)

    private val _loginClickChannel = Channel<Unit>(Channel.CONFLATED)
    val loginClickChannel = _loginClickChannel.receiveAsFlow()

    val isTopButtonVisible = MutableStateFlow(false)

    val isLogin = MutableStateFlow(false)

    private val _sortingType = MutableStateFlow(LiveFilterType.Sorting.SORTING_NEW)
    val sortingType
        get() = _sortingType.asStateFlow()

    private val _adultType = MutableStateFlow(LiveFilterType.AdultFilter.HIDE_ADULT_LIVE)
    val adultType
        get() = _adultType.asStateFlow()

    private fun requestGetConfig() = getConfigUseCase()
        .stateIn( // Flow 를 StateFlow 로 변환하는 작업
            scope = viewModelScope, //CoroutineScope 명시
            started = SharingStarted.WhileSubscribed(5_000), //Flow 로부터 언제부터 구독을 할지 명시(SharingStarted : Collector 가 등록되면 바로 시작, WhileSubscribed : Collector 가 없어지면 5초 후 중지)
            initialValue = Result.Loading()
        )

    fun getConfig() = viewModelScope.launch {
        requestGetConfig().collectLatest { type ->
            when (type) {
                is Result.Loading -> {
                    isShowProgress.value = true
                }

                is Result.Success -> {
                    isShowProgress.value = false
                    if (type.data?.result == true) {
                        Timber.d(TAG, type.data)

                        configApp.value = type.data
                    }
                }

                is Result.NetworkError -> {
                    isShowProgress.value = false
                    type.message?.let {
                        Timber.tag(TAG).e(it)
                        _networkState.send(false to it)
                    }
                }

                is Result.Error -> {
                    isShowProgress.value = false
                    type.message?.let {
                        Timber.tag(TAG).e(it)
                        _networkState.send(false to it)
                    }
                }
            }
        }
    }

    fun loginClick() = viewModelScope.launch {
        _loginClickChannel.send(Unit)
    }

    private fun requestLogout() = logoutUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Result.Loading()
        )

    fun logoutClick() = viewModelScope.launch {
        requestLogout().collectLatest { type ->
            when (type) {
                is Result.Loading -> {
                    isShowProgress.value = true
                }

                is Result.Success -> {
                    isShowProgress.value = false
                    if (type.data?.result == true) {
                        loginModel.value = LoginState.Guest
                        if (!dataStore.isMemoryId().first()) {
                            dataStore.saveId("")
                        }

                        dataStore.savePw("")
                        dataStore.saveAutoLogin(false)

                        _networkState.send(true to "로그아웃 되셨습니다.")
                    }
                }

                is Result.NetworkError -> {
                    isShowProgress.value = false
                    type.message?.let {
                        Timber.tag(TAG).e(it)
                        _networkState.send(false to it)
                    }
                }

                is Result.Error -> {
                    isShowProgress.value = false
                    type.message?.let {
                        Timber.tag(TAG).e(it)
                        _networkState.send(false to it)
                    }
                }
            }
        }
    }

    fun login() = viewModelScope.launch {
        val id = dataStore.getSaveId().first()
        val pw = dataStore.getSavePw().first()
        if (id.isNotEmpty() && pw.isNotEmpty()) {
            requestLogin(id, pw).collect { type ->
                when (type) {
                    is Result.Loading -> {
                        isShowProgress.value = true
                    }

                    is Result.Success -> {
                        isShowProgress.value = false

                        val data = type.data
                        _networkState.send(
                            if (data != null && data.result) {
                                loginModel.value = LoginState.Ok(data)
                                true to "로그인에 성공하셨습니다."
                            } else {
                                false to resource.getString(R.string.warning_auto_login)
                            }
                        )
                    }

                    is Result.NetworkError -> {
                        isShowProgress.value = false
                        type.message?.let {
                            _networkState.send(false to it)
                        }
                    }

                    is Result.Error -> {
                        isShowProgress.value = false
                        type.message?.let {
                            _networkState.send(false to it)
                        }
                    }
                }
            }
        }
    }

    val getAllLive =
        requestGetLiveRemoteUseCase(20, sortingType, adultType).cachedIn(scope = viewModelScope)

    //로그인 처리
    private fun requestLogin(id: String, pw: String) =
        requestMemberLoginUseCase(id, pw)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Result.Loading()
            )

    fun filterClick(type: LiveFilterType.Sorting) {
        _sortingType.value = type
    }

    fun adultSwitchCheck(type: LiveFilterType.AdultFilter) {
        _adultType.value = type
    }

    fun guestSwitchCheck(btn: CompoundButton, b: Boolean) {
        btn.isChecked = !b
        viewModelScope.launch {
            _loginClickChannel.send(Unit)
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}