package com.example.data.model.response

import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("adultAuth")
    val adultAuth: String,
    @SerializedName("base")
    val base: String,
    @SerializedName("channel")
    val channel: String,
    @SerializedName("chargeFull")
    val chargeFull: String,
    @SerializedName("chargeHeart")
    val chargeHeart: String,
    @SerializedName("chargeItem")
    val chargeItem: String,
    @SerializedName("chargeItemInfo")
    val chargeItemInfo: String,
    @SerializedName("chargeItemInfo/listUp")
    val chargeItemInfoListUp: String,
    @SerializedName("chargeItemInfo/userUp")
    val chargeItemInfoUserUp: String,
    @SerializedName("chargeItemInfo/saveUp")
    val chargeItemInfoSaveUp: String,
    @SerializedName("chargeItemInfo/fireRecom")
    val chargeItemInfoFireRecom: String,
    @SerializedName("chargeItemInfo/pushFan")
    val chargeItemInfoPushFan: String,
    @SerializedName("chargeItemInfo/pushBookmark")
    val chargeItemInfoPushBookMark: String,
    @SerializedName("chargeItemInfo/HeartEmo")
    val chargeItemInfoHeartEmo: String,
    @SerializedName("chargeItemInfo/listDeco")
    val chargeItemInfoListDeco: String,
    @SerializedName("chargeItemInfo/worldMsg")
    val chargeItemInfoWorldMsg: String,
    @SerializedName("chargeItemInfo/guestLive")
    val chargeItemInfoGuestLive: String,
    @SerializedName("chargeItemInfo/entUnlimit")
    val chargeItemInfoEntUnlimit: String,
    @SerializedName("chargeItemInfo/nickDeco")
    val chargeItemInfoNickDeco: String,
    @SerializedName("chargeItemInfo/introEffect")
    val chargeItemInfoIntroEffect: String,
    @SerializedName("event")
    val event: String,
    @SerializedName("exchange")
    val exchange: String,
    @SerializedName("findId")
    val findId: String,
    @SerializedName("findPw")
    val findPw: String,
    @SerializedName("freeLawConsult")
    val freeLawConsult: String,
    @SerializedName("itemLog")
    val itemLog: String,
    @SerializedName("join")
    val join: String,
    @SerializedName("notice")
    val notice: String,
    @SerializedName("policyMarketing")
    val policyMarketing: String,
    @SerializedName("policyPrivacy")
    val policyPrivacy: String,
    @SerializedName("policyService")
    val policyService: String,
    @SerializedName("post")
    val post: String,
    @SerializedName("reportCenter")
    val reportCenter: String,
    @SerializedName("signatureGuide")
    val signatureGuide: String,
    @SerializedName("socialLogin/FACEBOOK")
    val socialLoginFACEBOOK: String,
    @SerializedName("socialLogin/GOOGLE")
    val socialLoginGOOGLE: String,
    @SerializedName("socialLogin/KAKAO")
    val socialLoginKAKAO: String,
    @SerializedName("socialLogin/NAVER")
    val socialLoginNAVER: String
)