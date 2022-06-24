package com.example.data.db.entity.config

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "socialLogin")
data class SocialLoginEntity(
    @PrimaryKey
    val id: Long = 1,
    val FACEBOOK: Boolean,
    val GOOGLE: Boolean,
    val KAKAO: Boolean,
    val NAVER: Boolean
)