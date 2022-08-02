package com.example.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.db.converter.RoomConverters
import com.example.data.db.dao.live.LiveDataDao
import com.example.data.db.dao.live.LiveRemoteKeysDao
import com.example.data.db.entity.live.LiveListEntity
import com.example.data.db.entity.live.LiveRemoteKey

@Database(
    entities = [
        LiveListEntity::class,
        LiveRemoteKey::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class LiveDatabase : RoomDatabase() {
    abstract fun liveDataDao(): LiveDataDao
    abstract fun liveRemoteKeysDao(): LiveRemoteKeysDao
}