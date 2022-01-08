package com.example.airquality.data

import com.google.gson.annotations.SerializedName

data class Sensor(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("stationId")
    val stationId: Int = 0
)
