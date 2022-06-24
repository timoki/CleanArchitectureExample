package com.example.data.db.entity.config

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "server")
data class ServerEntity(
    @PrimaryKey
    val id: Long = 1,
    val api: String,
    val node: List<String>
)