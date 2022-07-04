package com.example.data.model.live

import com.google.gson.annotations.SerializedName

data class LiveList(
    @SerializedName("bookmarkCnt")
    val bookmarkCnt: Int,
    @SerializedName("browser")
    val browser: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("fanCnt")
    val fanCnt: Int,
    @SerializedName("heart")
    val heart: Int,
    @SerializedName("isAdult")
    val isAdult: Boolean,
    @SerializedName("isGuestLive")
    val isGuestLive: Boolean,
    @SerializedName("isLive")
    val isLive: Boolean,
    @SerializedName("isPw")
    val isPw: Boolean,
    @SerializedName("ivsThumbnail")
    val ivsThumbnail: String,
    @SerializedName("likeCnt")
    val likeCnt: Int,
    @SerializedName("listDeco")
    val listDeco: String,
    @SerializedName("listUp")
    val listUp: String,
    @SerializedName("liveType")
    val liveType: String,
    @SerializedName("playCnt")
    val playCnt: Int,
    @SerializedName("sizeHeight")
    val sizeHeight: Int,
    @SerializedName("sizeWidth")
    val sizeWidth: Int,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("storage")
    val storage: String,
    @SerializedName("thumbUrl")
    val thumbUrl: String,
    @SerializedName("thumbUrlOrigin")
    val thumbUrlOrigin: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("totalScoreCnt")
    val totalScoreCnt: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("user")
    val user: Int,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("userIdx")
    val userIdx: Int,
    @SerializedName("userImg")
    val userImg: String,
    @SerializedName("userLimit")
    val userLimit: Int,
    @SerializedName("userNick")
    val userNick: String,
    @SerializedName("userUp")
    val userUp: String
)