package com.example.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.db.dao.ConfigDataDao
import com.example.data.db.entity.config.*
import com.example.data.db.converter.RoomConverters
import com.example.data.db.entity.live.LiveListEntity

/**
 * Room Database 를 정의하고 DAO 를 반환한다
 * entities 파라메터에는 @Entity 어노테이션을 적용한 Entity Class 를 넣어야 하며 해당 Database Class 와 관련된 기능(Config)의 Entity 가 복수면 Array 형식으로 선언 가능하다.
 * @TypeConverters 어노테이션에는 Convert Class 를 지정 해준다.
 * */
@Database(
    entities = [
        ConfigDataEntity::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class ConfigDatabase : RoomDatabase() {
    abstract fun configDataDao(): ConfigDataDao
}