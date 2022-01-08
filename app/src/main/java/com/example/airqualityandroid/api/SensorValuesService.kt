package com.example.airquality.api
import com.example.airquality.data.value.Values
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SensorValuesService {
    @GET("pjp-api/rest/data/getData/{sensorId}")
    fun getSensorValues(@Path("sensorId") sensorId: String): Call<Values>;
}