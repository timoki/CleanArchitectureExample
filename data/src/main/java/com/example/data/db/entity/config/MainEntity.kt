package com.example.data.db.entity.config

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "configDataMain",
    foreignKeys = [
        ForeignKey(
            entity = AndroidEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"]
        ),
        ForeignKey(
            entity = WinEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"]
        )
    ]
)
data class MainEntity (
    @PrimaryKey
    val id: Long,
    val img: String,
    val url: String
)