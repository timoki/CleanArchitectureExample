package com.example.domain.model.config

data class DebugModel(
    val REQUEST_URI: String,
    val device: DeviceModel,
    val name: String,
    val query: Any,
    val queryCnt: Int,
    val queryErrorLib: List<Any>,
    val queryLib: List<Any>,
    val redis: List<String>,
    val xhprofLink: String
)