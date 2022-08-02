package com.example.data.db.entity.config

import androidx.room.ColumnInfo

data class CategoryNewEntity (
    @ColumnInfo(name = "code")
    val code: String,
    @ColumnInfo(name = "idx")
    val idx: Int,
    @ColumnInfo(name = "isList")
    val isList: Boolean,
    @ColumnInfo(name = "isView")
    val isView: Boolean,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "type")
    val type: String
)