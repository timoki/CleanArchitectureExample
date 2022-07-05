package com.example.domain.model.login

data class UserInfoModel(
    val agreeSmsYN: String,
    val authYN: String,
    val bjRank: Int,
    val channelDesc: String,
    val channelTitle: String,
    val chatYN: String,
    val coinHave: Int,
    val coinUse: Int,
    val id: String,
    val idx: Int,
    val imgProfile: String,
    val imgProfileYN: String,
    val isAdult: Boolean,
    val isBJ: String,
    val isLogin: Boolean,
    val nick: String,
    val postCountReadN: Int,
    val postYN: String,
    val purchaseUser: String,
    val recomYN: String,
    val socialYN: String
)