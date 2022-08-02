package com.example.data.db.entity.config

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class BannerEntity(
    @ColumnInfo(name = "android")
    val android: AndroidEntity,
    @ColumnInfo(name = "win")
    val win: WinEntity
)