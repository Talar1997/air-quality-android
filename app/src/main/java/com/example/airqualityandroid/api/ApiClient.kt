package com.example.airquality.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://api.gios.gov.pl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getStationService(): StationsService{
        return getRetrofit().create(StationsService::class.java)
    }

    fun getSensorsService(): SensorsService{
        return getRetrofit().create(SensorsService::class.java)
    }

    fun getSensorValuesService(): SensorValuesService{
        return getRetrofit().create(SensorValuesService::class.java)
    }

    fun getStationIndexService(): StationIndexService{
        return getRetrofit().create(StationIndexService::class.java)
    }
}