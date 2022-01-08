package com.example.airquality.data.station

import com.google.gson.annotations.SerializedName

data class Commune(
    @SerializedName("communeName")
    val communeName: String = "",
    @SerializedName("districtName")
    val districtName: String = "",
    @SerializedName("provinceName")
    val provinceName: String = ""
)
