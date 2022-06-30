package com.example.data.model.login

import com.google.gson.annotations.SerializedName

data class SiteMode(
    @SerializedName("mode")
    val mode: String?,
    @SerializedName("needAuth")
    val needAuth: Boolean?,
    @SerializedName("type")
    val type: String?
)