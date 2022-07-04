package com.example.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.db.dao.ConfigDataDao
import com.example.data.db.entity.config.*
import com.example.data.db.converter.RoomConverters
import com.example.data.db.dao.LiveDataDao
import com.example.data.db.entity.live.LiveListEntity

@Database(
    entities = [
        LiveListEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class LiveDatabase : RoomDatabase() {
    abstract fun liveDataDao(): LiveDataDao
}