package com.example.data.db.entity.config

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "configDataCategoryNew",
    foreignKeys = [
        ForeignKey(
            entity = ConfigDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"]
        )
    ]
)
data class CategoryNewEntity (
    @PrimaryKey
    val id: Long,
    val code: String,
    val idx: Int,
    val isList: Boolean,
    val isView: Boolean,
    val title: String,
    val type: String
)