package com.example.airquality.data.station

import com.google.gson.annotations.SerializedName
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Station(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("stationName")
    val stationName: String = "",
    @SerializedName("gegrLat")
    val gegrLat: String = "",
    @SerializedName("gegrLon")
    val gegrLon: String = "",
    @SerializedName("city")
    val city: City,
    @SerializedName("addressStreet")
    val addressStreet: String? = ""
){
    class Deserializer: ResponseDeserializable<Array<Station>> {
        override fun deserialize(content: String): Array<Station>? = Gson().fromJson(content, Array<Station>::class.java)
    }
}
