package com.example.data.db.entity.config

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.db.entity.config.AdultCheckEntity

/**
 * Entity 는 Local Database Data Class 라고 보면 된다.
 * */
@Entity(tableName = "configData")
data class ConfigDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 1,
    @ColumnInfo(name = "message")
    val message: String,
    @ColumnInfo(name = "result")
    val result: Boolean
)