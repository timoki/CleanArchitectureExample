package com.example.data.db.entity.live

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liveRemoteKeys")
data class LiveRemoteKey(
    @PrimaryKey
    val liveId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)