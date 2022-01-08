package com.example.airquality.data.station

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("provinceName")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("commune")
    val commune: Commune
)
