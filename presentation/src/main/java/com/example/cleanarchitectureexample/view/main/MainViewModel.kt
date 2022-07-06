package com.example.cleanarchitectureexample.view.main

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.utils.ResourceProvider
import com.example.data.db.database.DataStoreModule
import com.example.domain.model.base.Result
import com.example.domain.model.config.ConfigDataModel
import com.example.domain.model.live.LiveListModel
import com.example.domain.model.login.LoginDataModel
import com.example.domain.usecase.config.GetConfigUseCase
import com.example.domain.usecase.live.RequestGetLiveRemoteUseCase
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
    private val requestGetLiveRemoteUseCase: RequestGetLiveRemoteUseCase,
    private val dataStore: DataStoreModule,
    private val resource: ResourceProvider
): ViewModel() {

    val loginModel = MutableStateFlow<LoginDataModel?>(null)

    val configApp = MutableStateFlow<ConfigDataModel?>(null)

    val liveListModel = MutableStateFlow<PagingData<LiveListModel>?>(null)

    private val _networkError = Channel<String>(Channel.CONFLATED)
    val networkError = _networkError.receiveAsFlow()

    val isShowProgress = MutableStateFlow(false)

    private val _loginClickChannel = Channel<Unit>(Channel.CONFLATED)
    val loginClickChannel = _loginClickChannel.receiveAsFlow()

    val isTopButtonVisible = MutableStateFlow(false)

    private val _selectFilterTypePosition = MutableStateFlow(1)
    val selectFilterTypePosition
        get() = _selectFilterTypePosition.asStateFlow()

    private val _filterType = MutableStateFlow(SORTING_NEW)
    val filterType
        get() = _filterType.asStateFlow()

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
                    isShowProgress.value = true
                }

                is Result.Success -> {
                    isShowProgress.value = false
                    if (type.data?.result == true) {
                        Log.d(TAG, "${type.data}")

                        configApp.value = type.data
                    }
                }

                is Result.NetworkError -> {
                    isShowProgress.value = false
                    type.message?.let {
                        Log.e(TAG, it)
                        _networkError.send(it)
                    }
                }

                is Result.Error -> {
                    isShowProgress.value = false
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
                    isShowProgress.value = true
                }

                is Result.Success -> {
                    isShowProgress.value = false
                    if (type.data?.result == true) {
                        loginModel.value = null
                        if (!dataStore.isMemoryId().first()) {
                            dataStore.saveId("")
                        }

                        dataStore.savePw("")
                        dataStore.saveAutoLogin(false)
                    }
                }

                is Result.NetworkError -> {
                    isShowProgress.value = false
                    type.message?.let {
                        Log.e(TAG, it)
                        _networkError.send(it)
                    }
                }

                is Result.Error -> {
                    isShowProgress.value = false
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
        val pw = dataStore.getSavePw().first()
        if (id.isNotEmpty() && pw.isNotEmpty()) {
            requestLogin(id, pw).collect { type ->
                when (type) {
                    is Result.Loading -> {
                        isShowProgress.value = true
                    }

                    is Result.Success -> {
                        isShowProgress.value = false

                        if (type.data?.result == true) {
                            loginModel.value = type.data
                        } else {
                            _networkError.send(resource.getString(R.string.warning_auto_login))
                        }
                    }

                    is Result.NetworkError -> {
                        isShowProgress.value = false
                        type.message?.let {
                            _networkError.send(it)
                        }
                    }

                    is Result.Error -> {
                        isShowProgress.value = false
                        type.message?.let {
                            _networkError.send(it)
                        }
                    }
                }
            }
        }
    }

    fun getAllLive() = viewModelScope.launch {
        requestGetLiveRemoteUseCase().cachedIn(scope = viewModelScope).collect {
            liveListModel.value = it
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

    fun filterClick(view: View) {
        val filterType: String
        when (view.id) {
            R.id.sortingBest -> {
                filterType = SORTING_BEST
                _selectFilterTypePosition.value = 0
            }
            R.id.sortingNew -> {
                filterType = SORTING_NEW
                _selectFilterTypePosition.value = 1
            }
            R.id.sortingView -> {
                filterType = SORTING_VIEW
                _selectFilterTypePosition.value = 2
            }
            else -> filterType = SORTING_NEW
        }

        _filterType.value = filterType
    }

    companion object {
        private const val TAG = "MainViewModel"
        private const val SORTING_BEST = "scoreLiveRecomWeek"
        private const val SORTING_NEW = "startDateTime"
        private const val SORTING_VIEW = "userCnt"
    }
}