package com.example.data.db.entity.config

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

data class AndroidEntity (
    val main: List<MainEntity>
)