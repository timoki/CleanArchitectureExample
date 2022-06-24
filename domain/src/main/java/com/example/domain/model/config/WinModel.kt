package com.example.domain.model.config

data class WinModel(
    val main: List<MainModel>,
    val newMain: List<Any>,
    val subLeft: SubLeftAndRightModel,
    val subRight: SubLeftAndRightModel
)