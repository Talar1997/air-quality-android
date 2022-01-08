package com.example.airquality.api
import com.example.airquality.data.index.IndexLevel
import com.example.airquality.data.station.Station
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StationIndexService {

    @GET("pjp-api/rest/aqindex/getIndex/{stationId}")
    fun getStationIndex(@Path("stationId") stationId: String): Call<IndexLevel>;
}