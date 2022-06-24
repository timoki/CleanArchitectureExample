package com.example.domain.model.config

data class CategoryNewModel(
    val code: String,
    val idx: Int,
    val isList: Boolean,
    val isView: Boolean,
    val title: String,
    val type: String
)