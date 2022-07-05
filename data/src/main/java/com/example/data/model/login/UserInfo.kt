package com.example.data.model.login

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("agreeSmsYN")
    val agreeSmsYN: String,
    @SerializedName("authYN")
    val authYN: String,
    @SerializedName("bjRank")
    val bjRank: Int,
    @SerializedName("channelDesc")
    val channelDesc: String,
    @SerializedName("channelTitle")
    val channelTitle: String,
    @SerializedName("chatYN")
    val chatYN: String,
    @SerializedName("coinHave")
    val coinHave: Int,
    @SerializedName("coinUse")
    val coinUse: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("idx")
    val idx: Int,
    @SerializedName("imgProfile")
    val imgProfile: String,
    @SerializedName("imgProfileYN")
    val imgProfileYN: String,
    @SerializedName("isAdult")
    val isAdult: Boolean,
    @SerializedName("isBJ")
    val isBJ: String,
    @SerializedName("isLogin")
    val isLogin: Boolean,
    @SerializedName("nick")
    val nick: String,
    @SerializedName("postCountReadN")
    val postCountReadN: Int,
    @SerializedName("postYN")
    val postYN: String,
    @SerializedName("purchaseUser")
    val purchaseUser: String,
    @SerializedName("recomYN")
    val recomYN: String,
    @SerializedName("socialYN")
    val socialYN: String
)