package com.example.data.db.entity.config

import androidx.room.ColumnInfo

data class AdultCheckEntity(
    @ColumnInfo(name = "chatMessage")
    val chatMessage: Boolean?,
    @ColumnInfo(name = "post")
    val post: Boolean,
    @ColumnInfo(name = "recom")
    val recom: Boolean
)