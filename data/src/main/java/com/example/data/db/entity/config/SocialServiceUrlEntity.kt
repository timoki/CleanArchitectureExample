package com.example.data.db.entity.config

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "socialServiceUrl")
data class SocialServiceUrlEntity(
    @PrimaryKey
    val id: Long = 1,
    val facebook: String,
    val instagram: String,
    val naverBlog: String,
    val naverTV: String,
    val tiktok: String,
    val twitter: String,
    val youtube: String
)