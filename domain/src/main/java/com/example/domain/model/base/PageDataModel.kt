package com.example.domain.model.base

data class PageDataModel(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val page: Int,
    val lastPage: Int
)