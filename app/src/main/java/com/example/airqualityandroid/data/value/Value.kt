package com.example.airquality.data.value

import com.google.gson.annotations.SerializedName

data class Value(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("value")
    val value: Double? = 0.0
)
