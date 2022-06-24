package com.example.data.db.entity.config

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "adultCheck")
data class AdultCheckEntity(
    @PrimaryKey
    val id: Long = 1,
    @ColumnInfo(name = "chatMessage")
    val chatMessage: Boolean,
    @ColumnInfo(name = "post")
    val post: Boolean,
    @ColumnInfo(name = "recom")
    val recom: Boolean
)