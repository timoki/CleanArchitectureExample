package com.example.data.db.entity.live

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liveData")
data class LiveListEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "bookmarkCnt")
    val bookmarkCnt: Int,
    @ColumnInfo(name = "browser")
    val browser: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "code")
    val code: String,
    @ColumnInfo(name = "endTime")
    val endTime: String,
    @ColumnInfo(name = "fanCnt")
    val fanCnt: Int,
    @ColumnInfo(name = "heart")
    val heart: Int,
    @ColumnInfo(name = "isAdult")
    val isAdult: Boolean,
    @ColumnInfo(name = "isGuestLive")
    val isGuestLive: Boolean,
    @ColumnInfo(name = "isLive")
    val isLive: Boolean,
    @ColumnInfo(name = "isPw")
    val isPw: Boolean,
    @ColumnInfo(name = "ivsThumbnail")
    val ivsThumbnail: String,
    @ColumnInfo(name = "likeCnt")
    val likeCnt: Int,
    @ColumnInfo(name = "listDeco")
    val listDeco: String,
    @ColumnInfo(name = "listUp")
    val listUp: String,
    @ColumnInfo(name = "liveType")
    val liveType: String,
    @ColumnInfo(name = "playCnt")
    val playCnt: Int,
    @ColumnInfo(name = "sizeHeight")
    val sizeHeight: Int,
    @ColumnInfo(name = "sizeWidth")
    val sizeWidth: Int,
    @ColumnInfo(name = "startTime")
    val startTime: String,
    @ColumnInfo(name = "storage")
    val storage: String,
    @ColumnInfo(name = "thumbUrl")
    val thumbUrl: String,
    @ColumnInfo(name = "thumbUrlOrigin")
    val thumbUrlOrigin: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "totalScoreCnt")
    val totalScoreCnt: Int,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "user")
    val user: Int,
    @ColumnInfo(name = "userId")
    val userId: String,
    @ColumnInfo(name = "userIdx")
    val userIdx: Int,
    @ColumnInfo(name = "userImg")
    val userImg: String,
    @ColumnInfo(name = "userLimit")
    val userLimit: Int,
    @ColumnInfo(name = "userNick")
    val userNick: String,
    @ColumnInfo(name = "userUp")
    val userUp: String
)