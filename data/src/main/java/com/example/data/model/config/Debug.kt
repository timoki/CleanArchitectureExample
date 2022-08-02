package com.example.data.model.config

import com.google.gson.annotations.SerializedName

data class Debug(
    @SerializedName("REQUEST_URI")
    val REQUEST_URI: String,
    @SerializedName("device")
    val device: Device,
    @SerializedName("name")
    val name: String,
    @SerializedName("query")
    val query: String,
    @SerializedName("queryCnt")
    val queryCnt: Int,
    @SerializedName("queryErrorLib")
    val queryErrorLib: List<Any>,
    @SerializedName("queryLib")
    val queryLib: List<Any>,
    @SerializedName("redis")
    val redis: List<String>,
    @SerializedName("xhprofLink")
    val xhprofLink: String
)