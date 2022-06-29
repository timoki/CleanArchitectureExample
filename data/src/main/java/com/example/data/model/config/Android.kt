package com.example.data.model.config

import com.google.gson.annotations.SerializedName

data class Android(
    @SerializedName("main")
    val main: List<Main>
)