package com.example.domain.model.base

/**
 * API 통신 시 연결할 주소, Method 등 정리
 * */
object Constants {
    const val BASE_URL = "https://api.pandalive.co.kr/v1/"

    private const val CONFIG = "config/"
        const val configAppMethod = "${CONFIG}app"

    private const val MEMBER = "member/"
        const val checkIdMethod = "${MEMBER}check_id"
        const val checkNicknameMethod = "${MEMBER}check_nick"
        const val loginMethod = "${MEMBER}login"
        const val joinMethod = "${MEMBER}join"
        const val logoutMethod = "${MEMBER}logout"
}