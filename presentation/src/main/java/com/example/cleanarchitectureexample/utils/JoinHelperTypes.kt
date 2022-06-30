package com.example.cleanarchitectureexample.utils

import androidx.annotation.StringRes
import com.example.cleanarchitectureexample.R

enum class JoinHelperTypes(
    @StringRes val stringRes: Int
) {
    ID_NORMAL(R.string.id_check),
    PW_NORMAL(R.string.pw_check),
    PW_RE_NORMAL(R.string.pw_re),
    NICK_NORMAL(R.string.nick_check),

    ERROR_LOGIN_MUST(R.string.warning_loginMust),
    ERROR_RECAPTCHA(R.string.warning_recaptcha),
    ERROR_NEED_ADULT(R.string.warning_needAdult),
    ERROR_WRONG_ID(R.string.warning_wrongId),
    ERROR_WRONG_PW(R.string.warning_wrongPw),
    ERROR_EXISTS_ID(R.string.warning_existsId),
    ERROR_WRONG_NICK(R.string.warning_wrongNick),
    ERROR_EXISTS_NICK(R.string.warning_existsNick),
    ERROR_NO_ID(R.string.warning_noid),
    ERROR_DORMENT(R.string.warning_dormant),
    ERROR_NO_PW(R.string.warning_nopw),
    ERROR_OVERLAP(R.string.warning_overlap),
    ERROR_BLOCK_WORD(R.string.warning_blockWord),
    ERROR_PW_FORMAT(R.string.warning_pw_format),
    ERROR_PW_MATCH(R.string.warning_pw_match),
    ERROR_UNKNOWN_TYPE(R.string.warning_unknown)
}
