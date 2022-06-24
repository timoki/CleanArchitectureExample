package com.example.data.db.entity.config

import androidx.room.Embedded

data class WinEntity(
    val main: List<MainEntity>,
    val newMain: List<MainEntity>,
    @Embedded
    val subLeft: SubLeftAndRightEntity,
    @Embedded
    val subRight: SubLeftAndRightEntity
)