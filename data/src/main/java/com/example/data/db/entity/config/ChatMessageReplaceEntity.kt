package com.example.data.db.entity.config

import androidx.room.Embedded

data class ChatMessageReplaceEntity(
    @Embedded
    val ko: KoEntity
)