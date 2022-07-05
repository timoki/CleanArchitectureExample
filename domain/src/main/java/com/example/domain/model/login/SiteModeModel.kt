package com.example.domain.model.login

data class SiteModeModel(
    val mode: String,
    val needAuth: Boolean,
    val type: String
)