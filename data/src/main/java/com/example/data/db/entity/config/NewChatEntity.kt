package com.example.data.db.entity.config

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "newChat")
data class NewChatEntity(
    @PrimaryKey
    val id: Long = 1,
    val api: String,
    val node: String,
    val roomUserCount: String,
    val status: String
)