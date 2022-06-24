package com.example.data.db.entity.config

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "link")
data class LinkEntity(
    @PrimaryKey
    val id: Long = 1,
    val adultAuth: String,
    val base: String,
    val channel: String,
    val chargeFull: String,
    val chargeHeart: String,
    val chargeItem: String,
    val chargeItemInfo: String,
    @ColumnInfo(name = "chargeItemInfo/listUp")
    val chargeItemInfoListUp: String,
    @ColumnInfo(name = "chargeItemInfo/userUp")
    val chargeItemInfoUserUp: String,
    @ColumnInfo(name = "chargeItemInfo/saveUp")
    val chargeItemInfoSaveUp: String,
    @ColumnInfo(name = "chargeItemInfo/fireRecom")
    val chargeItemInfoFireRecom: String,
    @ColumnInfo(name = "chargeItemInfo/pushFan")
    val chargeItemInfoPushFan: String,
    @ColumnInfo(name = "chargeItemInfo/pushBookmark")
    val chargeItemInfoPushBookMark: String,
    @ColumnInfo(name = "chargeItemInfo/HeartEmo")
    val chargeItemInfoHeartEmo: String,
    @ColumnInfo(name = "chargeItemInfo/listDeco")
    val chargeItemInfoListDeco: String,
    @ColumnInfo(name = "chargeItemInfo/worldMsg")
    val chargeItemInfoWorldMsg: String,
    @ColumnInfo(name = "chargeItemInfo/guestLive")
    val chargeItemInfoGuestLive: String,
    @ColumnInfo(name = "chargeItemInfo/entUnlimit")
    val chargeItemInfoEntUnlimit: String,
    @ColumnInfo(name = "chargeItemInfo/nickDeco")
    val chargeItemInfoNickDeco: String,
    @ColumnInfo(name = "chargeItemInfo/introEffect")
    val chargeItemInfoIntroEffect: String,
    val event: String,
    val exchange: String,
    val findId: String,
    val findPw: String,
    val freeLawConsult: String,
    val itemLog: String,
    val join: String,
    val notice: String,
    val policyMarketing: String,
    val policyPrivacy: String,
    val policyService: String,
    val post: String,
    val reportCenter: String,
    val signatureGuide: String,
    @ColumnInfo(name = "socialLogin/FACEBOOK")
    val socialLoginFACEBOOK: String,
    @ColumnInfo(name = "socialLogin/GOOGLE")
    val socialLoginGOOGLE: String,
    @ColumnInfo(name = "socialLogin/KAKAO")
    val socialLoginKAKAO: String,
    @ColumnInfo(name = "socialLogin/NAVER")
    val socialLoginNAVER: String
)