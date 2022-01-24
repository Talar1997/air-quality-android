package com.example.airquality.api
import com.example.airqualityandroid.data.sensor.Sensor
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SensorsService {
    @GET("pjp-api/rest/station/sensors/{stationId}")
    fun getSensor(@Path("stationId") stationId: String): Call<List<Sensor>>
}