package com.example.airquality.data.value

import com.google.gson.annotations.SerializedName

data class Values(
//    @SerializedName("id")
//    val id: Int = 0,
    @SerializedName("key")
    val key: String = "",
    @SerializedName("values")
    val values: List<Value>
)
