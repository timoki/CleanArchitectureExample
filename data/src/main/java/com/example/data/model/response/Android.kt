package com.example.data.model.response

import com.example.domain.model.config.MainModel
import com.google.gson.annotations.SerializedName

data class Android(
    @SerializedName("main")
    val main: List<Main>
)