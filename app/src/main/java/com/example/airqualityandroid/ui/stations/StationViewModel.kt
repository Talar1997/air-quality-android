package com.example.airqualityandroid.ui.stations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airquality.api.ApiClient
import com.example.airquality.data.station.Station
import retrofit2.Call
import retrofit2.Callback

//https://developer.android.com/topic/libraries/architecture/viewmodel
//https://developer.android.com/topic/libraries/architecture/livedata
//https://howtodoandroid.com/mvvm-retrofit-recyclerview-kotlin/
class StationViewModel : ViewModel() {

    private val stations: MutableLiveData<List<Station>> by lazy {
        MutableLiveData<List<Station>>().also{
            loadStations()
        }
    }

    private fun loadStations(){
        val stationApiCall: Call<List<Station>> = ApiClient().getStationService().getAllStations()
        stationApiCall.enqueue(object : Callback<List<Station>> {
            override fun onFailure(call: Call<List<Station>>?, t: Throwable?) {
                TODO("Not implemented yet")
            }

            override fun onResponse(
                call: Call<List<Station>>,
                response: retrofit2.Response<List<Station>>
            ) {
                val list: List<Station> = response.body()!!
                stations.postValue(list)
            }
        })
    }

    fun getStations(): LiveData<List<Station>> {
        return stations;
    }
}