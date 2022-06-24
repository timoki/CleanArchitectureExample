package com.example.data.db.entity.config

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatMessageEntity(
    @PrimaryKey
    val id: Long = 1,
    val intro: String
)