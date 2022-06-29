package com.example.cleanarchitectureexample.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.base.Result
import com.example.domain.model.login.LoginDataModel
import com.example.domain.usecase.member.RequestMemberCheckIdUseCase
import com.example.domain.usecase.member.RequestMemberCheckNickUseCase
import com.example.domain.usecase.member.RequestMemberJoinUseCase
import com.example.domain.usecase.member.RequestMemberLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignDialogViewModel @Inject constructor(
    private val requestMemberCheckIdUseCase: RequestMemberCheckIdUseCase,
    private val requestMemberCheckNickUseCase: RequestMemberCheckNickUseCase,
    private val loginUseCase: RequestMemberLoginUseCase,
    private val joinUseCase: RequestMemberJoinUseCase
) : ViewModel() {

    /** 기본 */
    //상단 탭 클릭한 타입(true : 로그인 탭, false : 회원가입 탭)
    private val _isSelectedToLoginTab = MutableStateFlow(true)
    val isSelectedToLoginTab
        get() = _isSelectedToLoginTab.asStateFlow()

    //프로그래스 바 표시여부
    private val _isShowProgress = MutableStateFlow(false)
    val isShowProgress
        get() = _isShowProgress.asStateFlow()

    //API 통신 에러 채널
    private val _networkError = Channel<String>(Channel.CONFLATED)
    val networkError = _networkError.receiveAsFlow()

    /** 로그인 */
    //로그인 성공 채널
    private val _loginSuccess = Channel<LoginDataModel?>(Channel.CONFLATED)
    val loginSuccess = _loginSuccess.receiveAsFlow()

    //로그인 아이디 text
    private val _loginIdText = MutableLiveData<String>()
    val loginIdText
        get() = _loginIdText

    //로그인 비밀번호 text
    private val _loginPwText = MutableLiveData<String>()
    val loginPwText
        get() = _loginPwText

    //아이디 저장 체크
    private val _isIdSaveChecked = MutableStateFlow(false)
    val isIdSaveChecked
        get() = _isIdSaveChecked.asStateFlow()

    //자동 로그인 체크
    private val _isAutoLoginChecked = MutableStateFlow(false)
    val isAutoLoginChecked
        get() = _isAutoLoginChecked.asStateFlow()

    //아이디 찾기 클릭 시 전달
    private val _findIdChannel = Channel<Unit>(Channel.CONFLATED)
    val findIdChannel = _findIdChannel.receiveAsFlow()

    //비밀번호 찾기 클릭 시 전달
    private val _findPwChannel = Channel<Unit>(Channel.CONFLATED)
    val findPwChannel = _findPwChannel.receiveAsFlow()

    /** 회원가입 */
    //회원가입 아이디 text
    private val _joinIdText = MutableLiveData<String>()
    val joinIdText
        get() = _joinIdText

    //회원가입 비밀번호 text
    private val _joinPwText = MutableLiveData<String>()
    val joinPwText
        get() = _joinPwText

    //회원가입 비밀번호 확인 text
    private val _joinPwReText = MutableLiveData<String>()
    val joinPwReText
        get() = _joinPwReText

    //회원가입 닉네임 text
    private val _joinNickText = MutableLiveData<String>()
    val joinNickText
        get() = _joinNickText

    //회원가입 전체동의
    private val _allAgree = MutableStateFlow(false)
    val allAgree
        get() = _allAgree.asStateFlow()

    //회원가입 서비스 이용약관 동의
    private val _agree1 = MutableStateFlow(false)
    val agree1
        get() = _agree1.asStateFlow()

    //회원가입 개인정보 처리방침 동의
    private val _agree2 = MutableStateFlow(false)
    val agree2
        get() = _agree2.asStateFlow()

    //회원가입 만 14세 이상 체크
    private val _agree3 = MutableStateFlow(false)
    val agree3
        get() = _agree3.asStateFlow()

    //회원가입 무료하트 이벤트 동의
    private val _agree4 = MutableStateFlow(false)
    val agree4
        get() = _agree4.asStateFlow()

    /** 기본 */
    //로그인 탭 클릭
    fun loginTabClick() {
        _isSelectedToLoginTab.value = true
    }

    //회원가입 탭 클릭
    fun joinTabClick() {
        _isSelectedToLoginTab.value = false
    }

    /** 로그인 */
    //로그인 버튼 클릭
    fun loginBtnClick() = viewModelScope.launch {
        if (!loginIdText.value.isNullOrEmpty() && !loginPwText.value.isNullOrEmpty()) {
            requestLogin().collectLatest { type ->
                when (type) {
                    is Result.Loading -> {
                        _isShowProgress.value = true
                    }

                    is Result.Success -> {
                        _isShowProgress.value = false
                        if (type.data?.result == true) {
                            _loginSuccess.send(type.data)
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
    private fun requestLogin() = loginUseCase(loginIdText.value ?: "", loginPwText.value ?: "")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Result.Loading()
        )


    //아이디 찾기 클릭
    fun findIdClick() {

    }

    //비밀번호 찾기 클릭
    fun findPwClick() {

    }

    /** 회원가입 */
    //전체동의 클릭
    fun onAllAgree() {
        _allAgree.value = !allAgree.value
        _agree1.value = allAgree.value
        _agree2.value = allAgree.value
        _agree3.value = allAgree.value
        _agree4.value = allAgree.value
    }

    //서비스 이용약관 동의 클릭
    fun onServiceAgree() {
        _agree1.value = !agree1.value
        isAllAgree()
    }

    //개인정보 처리방침 동의 클릭
    fun onPrivacyAgree() {
        _agree2.value = !agree2.value
        isAllAgree()
    }

    //만 14세 이상입니다 클릭
    fun onAdultConsentAgree() {
        _agree3.value = !agree3.value
        isAllAgree()
    }

    //무료하트 및 하트 이벤트 정보 알림 동의 클릭
    fun onFreeHeartAgreement() {
        _agree4.value = !agree4.value
        isAllAgree()
    }

    //전체동의 변경 함수
    private fun isAllAgree() {
        _allAgree.value = agree1.value && agree2.value && agree3.value && agree4.value
    }
}