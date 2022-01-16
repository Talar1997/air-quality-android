package com.example.airqualityandroid.data

import com.google.gson.annotations.SerializedName

data class SensorParam (
    @SerializedName("paramFormula")
    val paramFormula: String = "",

    @SerializedName("paramCode")
    val paramCode: String = ""
)