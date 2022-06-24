package com.example.data.db.entity.config

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "banner")
data class BannerEntity(
    @PrimaryKey
    val id: Long = 1,
    @ColumnInfo(name = "android")
    val android: AndroidEntity,
    @ColumnInfo(name = "win")
    val win: WinEntity
)