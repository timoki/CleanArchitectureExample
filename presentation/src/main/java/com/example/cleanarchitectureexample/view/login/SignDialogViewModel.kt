package com.example.cleanarchitectureexample.view.login

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.utils.JoinHelperTypes
import com.example.cleanarchitectureexample.utils.ResourceProvider
import com.example.data.db.database.DataStoreModule
import com.example.domain.model.base.Result
import com.example.domain.model.config.ConfigDataModel
import com.example.domain.model.login.LoginDataModel
import com.example.domain.usecase.member.RequestMemberCheckIdUseCase
import com.example.domain.usecase.member.RequestMemberCheckNickUseCase
import com.example.domain.usecase.member.RequestMemberJoinUseCase
import com.example.domain.usecase.member.RequestMemberLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignDialogViewModel @Inject constructor(
    application: Application,
    private val dataStore: DataStoreModule,
    private val requestMemberCheckIdUseCase: RequestMemberCheckIdUseCase,
    private val requestMemberCheckNickUseCase: RequestMemberCheckNickUseCase,
    private val requestMemberLoginUseCase: RequestMemberLoginUseCase,
    private val requestMemberJoinUseCase: RequestMemberJoinUseCase,
    private val resource: ResourceProvider
) : AndroidViewModel(application) {

    val configApp = MutableStateFlow<ConfigDataModel?>(null)

    var kakao = false
    var naver = false
    var google = false
    var facebook = false
    /** 기본 */
    //상단 X 아이콘 클릭
    private val _closeDialog = Channel<Unit>(Channel.CONFLATED)
    val closeDialog = _closeDialog.receiveAsFlow()

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
    val loginIdText = MutableStateFlow("")

    //로그인 비밀번호 text
    val loginPwText = MutableStateFlow("")

    //로그인 아이디 헬퍼 텍스트
    private val _loginIdHelperText = MutableStateFlow("")
    val loginIdHelperText
        get() = _loginIdHelperText.asStateFlow()

    //로그인 비밀번호 헬퍼 텍스트
    private val _loginPwHelperText = MutableStateFlow("")
    val loginPwHelperText
        get() = _loginPwHelperText.asStateFlow()

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
    //회원가입 성공 채널
    private val _joinSuccess = Channel<Unit>(Channel.CONFLATED)
    val joinSuccess = _joinSuccess.receiveAsFlow()

    //회원가입 아이디 text
    val joinIdText = MutableStateFlow("")

    //회원가입 비밀번호 text
    val joinPwText = MutableStateFlow("")

    //회원가입 비밀번호 확인 text
    val joinPwReText = MutableStateFlow("")

    //회원가입 닉네임 text
    val joinNickText = MutableStateFlow("")

    //회원가입 아이디 헬퍼 텍스트
    private val _joinIdHelperText = MutableStateFlow(JoinHelperTypes.ID_NORMAL)
    val joinIdHelperText
        get() = _joinIdHelperText.asStateFlow()

    //회원가입 비밀번호 헬퍼 텍스트
    private val _joinPwHelperText = MutableStateFlow(JoinHelperTypes.PW_NORMAL)
    val joinPwHelperText
        get() = _joinPwHelperText.asStateFlow()

    //회원가입 비밀번호 확인 헬퍼 텍스트
    private val _joinPwReHelperText = MutableStateFlow(JoinHelperTypes.PW_RE_NORMAL)
    val joinPwReHelperText
        get() = _joinPwReHelperText.asStateFlow()

    //회원가입 닉네임 헬퍼 텍스트
    private val _joinNickHelperText = MutableStateFlow(JoinHelperTypes.NICK_NORMAL)
    val joinNickHelperText
        get() = _joinNickHelperText.asStateFlow()

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

    //회원가입 버튼 활성화
    private val _isJoinButtonEnabled = MutableStateFlow(false)
    val isJoinButtonEnabled
        get() = _isJoinButtonEnabled.asStateFlow()

    private val _actionWebView = Channel<View?>(Channel.CONFLATED)
    val actionWebView = _actionWebView.receiveAsFlow()

    /** 기본 */
    //상단 X 아이콘 클릭
    fun closeButtonClick() = viewModelScope.launch {
        _closeDialog.send(Unit)
    }

    //로그인 탭 클릭
    fun loginTabClick() {
        _isSelectedToLoginTab.value = true
    }

    //회원가입 탭 클릭
    fun joinTabClick() {
        _isSelectedToLoginTab.value = false
    }

    init {
        viewModelScope.launch {
            loginIdText.value = dataStore.getSaveId().first()
            _isIdSaveChecked.value = dataStore.isMemoryId().first()
            _isAutoLoginChecked.value = dataStore.isAutoLogin().first()
        }
    }

    /** 로그인 */
    //로그인 버튼 클릭
    fun loginBtnClick() = viewModelScope.launch {
        if (loginIdText.value.isNotEmpty() && loginPwText.value.isNotEmpty()) {
            requestLogin().collect { type ->
                when (type) {
                    is Result.Loading -> {
                        _isShowProgress.value = true
                    }

                    is Result.Success -> {
                        _isShowProgress.value = false
                        _loginIdHelperText.value = ""
                        _loginPwHelperText.value = ""

                        if (type.data?.result == true) {
                            dataStore.apply {
                                if (isIdSaveChecked.value) {
                                    saveId(loginIdText.value)
                                }

                                if (isAutoLoginChecked.value) {
                                    savePw(loginPwText.value)
                                }
                            }

                            _loginSuccess.send(type.data)
                            return@collect
                        }

                        _loginIdHelperText.value = ""
                        _loginPwHelperText.value = ""

                        when (type.data?.errorData?.code) {
                            "noid", "wrongId" -> {
                                _loginIdHelperText.value = type.data?.message ?: resource.getString(
                                    R.string.warning_wrongId
                                )
                            }

                            "nopw", "wrongPw" -> {
                                _loginPwHelperText.value = type.data?.message ?: resource.getString(
                                    R.string.warning_wrongPw
                                )
                            }
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
    private fun requestLogin() =
        requestMemberLoginUseCase(loginIdText.value, loginPwText.value)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Result.Loading()
            )


    //웹뷰 이동
    fun onSendWebView(view: View?) = viewModelScope.launch {
        _actionWebView.send(view)
    }

    //아이디저장, 자동로그인 체크
    fun checkAutoLogin() = viewModelScope.launch(Dispatchers.IO) {
        dataStore.isAutoLogin().collect { b ->
            if (b) {
                dataStore.getSaveId().collect { id ->
                    loginIdText.value = id
                }

                dataStore.getSavePw().collect { pw ->
                    loginPwText.value = pw
                }

                loginBtnClick()
            } else {
                dataStore.isMemoryId().collect { bool ->
                    if (bool) {
                        dataStore.getSaveId().collect { id ->
                            loginIdText.value = id
                        }
                    }
                }
            }
        }
    }

    /** 회원가입 */
    //전체동의 클릭
    fun onAllAgree() {
        _allAgree.value = !allAgree.value
        _agree1.value = allAgree.value
        _agree2.value = allAgree.value
        _agree3.value = allAgree.value
        _agree4.value = allAgree.value
        joinBtnEnabledCheck()
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
        joinBtnEnabledCheck()
    }

    //에러타입 리턴 함수
    private fun getErrorType(type: String?): JoinHelperTypes {
        return when (type) {
            "loginMust" -> { // 로그인필요
                JoinHelperTypes.ERROR_LOGIN_MUST
            }

            "recaptcha" -> { // 자동 접속 방지 체크필요
                JoinHelperTypes.ERROR_RECAPTCHA
            }

            "needAdult" -> { // 본인인증 필요
                JoinHelperTypes.ERROR_NEED_ADULT
            }

            "wrongId" -> { // 잘못된 아이디
                JoinHelperTypes.ERROR_WRONG_ID
            }

            "wrongPw" -> { // 잘못된 비밀번호
                JoinHelperTypes.ERROR_WRONG_PW
            }

            "existsId" -> { // 이미 가입된 아이디
                JoinHelperTypes.ERROR_EXISTS_ID
            }

            "wrongNick" -> { // 잘못된 닉네임
                JoinHelperTypes.ERROR_WRONG_NICK
            }

            "existsNick" -> { // 존재하는 닉네임
                JoinHelperTypes.ERROR_EXISTS_NICK
            }

            "noid" -> { // 존재하지 않는 사용자
                JoinHelperTypes.ERROR_NO_ID
            }

            "dormant" -> { // 휴면계정
                JoinHelperTypes.ERROR_DORMENT
            }

            "nopw" -> { // 잘못입력된 비밀번호
                JoinHelperTypes.ERROR_NO_PW
            }

            "overlap" -> { // 연속가입방지
                JoinHelperTypes.ERROR_OVERLAP
            }

            "blockWord" -> { // 금칙어
                JoinHelperTypes.ERROR_BLOCK_WORD
            }

            else -> JoinHelperTypes.ERROR_UNKNOWN_TYPE
        }
    }

    //아이디 체크 처리
    private fun requestCheckId(id: String) = requestMemberCheckIdUseCase(id)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Result.Loading()
        )

    //아이디 체크 결과
    private fun checkId(id: String) = viewModelScope.launch {
        if (id.isNotEmpty()) {
            requestCheckId(id).collect { type ->
                when (type) {
                    is Result.Loading -> {
                        _isShowProgress.value = true
                    }

                    is Result.Success -> {
                        _isShowProgress.value = false
                        type.data?.let {
                            if (it.result) {
                                _joinIdHelperText.value = JoinHelperTypes.ID_NORMAL
                            } else {
                                _joinIdHelperText.value = getErrorType(it.errorData?.code)
                            }
                        }

                        joinBtnEnabledCheck()
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

    //회원가입 아이디 와쳐
    val joinIdTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(et: Editable?) {
            if (!et.isNullOrEmpty()) {
                checkId(et.toString())
            } else {
                _joinIdHelperText.value = JoinHelperTypes.ID_NORMAL
            }
        }
    }

    //회원가입 비밀번호 와쳐
    val joinPwTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(et: Editable?) {
            if (!et.isNullOrEmpty()) {
                val pwRegex = Regex("""^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#${'$'}%^&*])(?=\S+${'$'}).{8,15}$""")

                if (et.toString().matches(pwRegex)) {
                    _joinPwHelperText.value = JoinHelperTypes.PW_NORMAL
                } else {
                    _joinPwHelperText.value = JoinHelperTypes.ERROR_PW_FORMAT
                }
            } else {
                _joinPwHelperText.value = JoinHelperTypes.PW_NORMAL
            }
            joinBtnEnabledCheck()
        }
    }

    //회원가입 비밀번호 확인 와쳐
    val joinPwReTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(et: Editable?) {
            if (!et.isNullOrEmpty()) {
                if (joinPwText.value == et.toString()) {
                    _joinPwReHelperText.value = JoinHelperTypes.PW_RE_NORMAL
                } else {
                    _joinPwReHelperText.value = JoinHelperTypes.ERROR_PW_MATCH
                }
            } else {
                _joinPwReHelperText.value = JoinHelperTypes.PW_RE_NORMAL
            }

            joinBtnEnabledCheck()
        }
    }

    //아이디 체크 처리
    private fun requestCheckNick(nick: String) = requestMemberCheckNickUseCase(nick)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Result.Loading()
        )

    //아이디 체크 결과
    private fun checkNick(nick: String) = viewModelScope.launch {
        if (nick.isNotEmpty()) {
            requestCheckNick(nick).collect { type ->
                when (type) {
                    is Result.Loading -> {
                        _isShowProgress.value = true
                    }

                    is Result.Success -> {
                        _isShowProgress.value = false
                        type.data?.let {
                            if (it.result) {
                                _joinNickHelperText.value = JoinHelperTypes.NICK_NORMAL
                            } else {
                                _joinNickHelperText.value = getErrorType(it.errorData?.code)
                            }
                        }

                        joinBtnEnabledCheck()
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

    //회원가입 닉네임 와쳐
    val joinNickTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(et: Editable?) {
            if (!et.isNullOrEmpty()) {
                checkNick(et.toString())
            } else {
                _joinNickHelperText.value = JoinHelperTypes.ID_NORMAL
            }
        }
    }

    //회원가입 버튼 Enable 검사
    private fun joinBtnEnabledCheck() {
        _isJoinButtonEnabled.value =
            joinIdText.value.isNotEmpty() &&
                    joinIdHelperText.value == JoinHelperTypes.ID_NORMAL &&
                    joinPwText.value.isNotEmpty() &&
                    joinPwHelperText.value == JoinHelperTypes.PW_NORMAL &&
                    joinPwReText.value.isNotEmpty() &&
                    joinPwReHelperText.value == JoinHelperTypes.PW_RE_NORMAL &&
                    joinNickText.value.isNotEmpty() &&
                    joinNickHelperText.value == JoinHelperTypes.NICK_NORMAL &&
                    agree1.value &&
                    agree2.value &&
                    agree3.value
    }

    //회원가입 버튼 클릭
    fun joinButtonClick() = viewModelScope.launch {
        requestJoin().collect { type ->
            when (type) {
                is Result.Loading -> {
                    _isShowProgress.value = true
                }

                is Result.Success -> {
                    _isShowProgress.value = false
                    type.data?.let {
                        if (it.result) {
                            _joinSuccess.send(Unit)
                            responseJoin()
                            loginBtnClick()
                        } else {
                            _networkError.send(it.errorData?.code ?: "알수 없는 오류")
                        }
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

    //회원가입 처리
    private fun requestJoin() = requestMemberJoinUseCase(
        id = joinIdText.value,
        nick = joinNickText.value,
        pw = joinPwText.value,
        pwRe = joinPwReText.value,
        agreeSmsYN = if (agree4.value) "Y" else "N"
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Result.Loading()
    )

    //회원가입 완료 후 로그인 정보 세팅 -> 회원 가입 성공 시 자동 로그인 -> dismiss()
    private fun responseJoin() {
        loginIdText.value = joinIdText.value
        loginPwText.value = joinPwText.value

        joinIdText.value = ""
        joinPwText.value = ""
        joinPwReText.value = ""
        joinNickText.value = ""
        _allAgree.value = false
        _agree1.value = false
        _agree2.value = false
        _agree3.value = false
        _agree4.value = false
        _isJoinButtonEnabled.value = false

        _isSelectedToLoginTab.value = true
    }

    fun onSaveIdClick() = viewModelScope.launch {
        _isIdSaveChecked.value = !isIdSaveChecked.value
        if (!isIdSaveChecked.value) {
            dataStore.saveId("")
        }

        dataStore.saveMemoryId(isIdSaveChecked.value)
    }

    fun onAutoLoginClick() = viewModelScope.launch {
        _isAutoLoginChecked.value = !isAutoLoginChecked.value
        if (isAutoLoginChecked.value && !isIdSaveChecked.value) _isIdSaveChecked.value = true
        dataStore.saveAutoLogin(isAutoLoginChecked.value)
        dataStore.saveMemoryId(isIdSaveChecked.value)
    }
}