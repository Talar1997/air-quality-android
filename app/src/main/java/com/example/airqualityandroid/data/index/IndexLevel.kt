package com.example.airquality.data.index

import com.google.gson.annotations.SerializedName

data class IndexLevel(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("stCalcDate")
    val stCalcDate: String = "",

    @SerializedName("stIndexLevel")
    val stIndexLevel: ParamIndexLevel?,

    @SerializedName("so2IndexLevel")
    val so2IndexLevel: ParamIndexLevel?,

    @SerializedName("no2IndexLevel")
    val no2IndexLevel: ParamIndexLevel?,

    @SerializedName("coIndexLevel")
    val coIndexLevel: ParamIndexLevel?,

    @SerializedName("pm10IndexLevel")
    val pm10IndexLevel: ParamIndexLevel?,

    @SerializedName("pm25IndexLevel")
    val pm25IndexLevel: ParamIndexLevel?,
)
