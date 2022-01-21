package com.example.airqualityandroid.ui.home

import android.annotation.SuppressLint
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airquality.api.ApiClient
import com.example.airquality.data.MeasurementPair
import com.example.airquality.data.index.IndexLevel
import com.example.airquality.data.station.Station
import com.example.airqualityandroid.api.MeasurementKey
import com.example.airqualityandroid.data.StationDistance
import com.example.airqualityandroid.data.StationWithIndex
import com.google.android.gms.location.FusedLocationProviderClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var allStations: List<Station>
    var favStationsIds: List<Int> = mutableListOf()


    private val nearestStation: MutableLiveData<StationWithIndex> by lazy {
        MutableLiveData<StationWithIndex>().also{
            this.findNearestStation()
        }
    }

    private val favStations: MutableLiveData<List<StationWithIndex>> by lazy {
        MutableLiveData<List<StationWithIndex>>().also{
            loadFavouriteStations(favStationsIds)
        }
    }
    @SuppressLint("MissingPermission")
    private fun findNearestStation(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if(location !== null){
//                    getNearestStationByLocation(location)
                    val distanceList = allStations.map {
                        val stationLocation = Location("")
                        stationLocation.latitude = it.gegrLat.toDouble()
                        stationLocation.longitude = it.gegrLon.toDouble()
                        StationDistance(it, location.distanceTo(stationLocation))
                    }.sortedBy { it.distance }
                    val nearest = distanceList[0].station

                    loadNearestStationIndex(nearest)
                }
            }
    }

    private fun loadNearestStationIndex(station: Station){
        val stationIndexData: Call<IndexLevel> =
            ApiClient().getStationIndexService().getStationIndex(station.id.toString())

        stationIndexData.enqueue(object : Callback<IndexLevel> {
            override fun onResponse(call: Call<IndexLevel>, response: Response<IndexLevel>) {
                val response: IndexLevel = response.body()!!
                var measurement: MeasurementPair? = null

                if (response.stIndexLevel != null) {
                    measurement = MeasurementPair(MeasurementKey.ST_INDEX,
                        response.stIndexLevel.indexLevelName,
                        response.stIndexLevel.id,
                        null)
                }

                nearestStation.postValue(StationWithIndex(station, measurement))
            }

            override fun onFailure(call: Call<IndexLevel>, t: Throwable) {
                TODO("Not implemented yet")
            }
        })
    }

    private fun loadFavouriteStations(stationIds: List<Int>){
        val favStationsFiltered = allStations.filter { it.id in stationIds }
        val stationsWithIndex: MutableList<StationWithIndex> = mutableListOf()

        favStationsFiltered.forEach { station ->
            val stationIndexData: Call<IndexLevel> =
                ApiClient().getStationIndexService().getStationIndex(station.id.toString())

            stationIndexData.enqueue(object : Callback<IndexLevel> {
                override fun onResponse(call: Call<IndexLevel>, response: Response<IndexLevel>) {
                    val response: IndexLevel = response.body()!!
                    var measurement: MeasurementPair? = null

                    if (response.stIndexLevel != null) {
                        measurement = MeasurementPair(MeasurementKey.ST_INDEX,
                            response.stIndexLevel.indexLevelName,
                            response.stIndexLevel.id,
                            null)
                    }
                    stationsWithIndex.add(StationWithIndex(station, measurement))
                    favStations.postValue(stationsWithIndex)
                }

                override fun onFailure(call: Call<IndexLevel>, t: Throwable) {
                    TODO("Not implemented yet")
                }
            })
        }
    }

    fun getNearestStation(): LiveData<StationWithIndex>{
        return nearestStation
    }

    fun getFavouriteStations(): LiveData<List<StationWithIndex>>{
        return favStations
    }
}