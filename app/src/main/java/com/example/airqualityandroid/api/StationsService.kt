package com.example.airquality.api
import com.example.airquality.data.station.Station
import retrofit2.Call
import retrofit2.http.GET

interface StationsService {
    @GET("pjp-api/rest/station/findAll")
    fun getAllStations(): Call<List<Station>>;
}