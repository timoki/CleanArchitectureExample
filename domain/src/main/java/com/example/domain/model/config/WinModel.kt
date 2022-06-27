package com.example.domain.model.config

data class WinModel(
    val main: List<MainModel>,
    val newMain: List<MainModel>,
    val subLeft: SubLeftAndRightModel,
    val subRight: SubLeftAndRightModel
)