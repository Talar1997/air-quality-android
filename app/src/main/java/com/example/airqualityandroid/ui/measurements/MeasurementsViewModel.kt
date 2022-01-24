package com.example.airqualityandroid.ui.measurements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airquality.api.ApiClient
import com.example.airqualityandroid.data.measurement.MeasurementPair
import com.example.airqualityandroid.data.sensor.Sensor
import com.example.airquality.data.index.IndexLevel
import com.example.airquality.data.value.Values
import com.example.airqualityandroid.data.measurement.MeasurementKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MeasurementsViewModel : ViewModel() {
    var stationId = -1

    private val measurements: MutableLiveData<List<MeasurementPair>> by lazy {
        MutableLiveData<List<MeasurementPair>>().also{
            loadMeasurements(stationId)
        }
    }

    private fun loadMeasurements(stationId: Int){
        val stationIndexData: Call<IndexLevel> =
            ApiClient().getStationIndexService().getStationIndex(stationId.toString())

        stationIndexData.enqueue(object : Callback<IndexLevel> {
            override fun onResponse(call: Call<IndexLevel>, response: Response<IndexLevel>) {
                val response: IndexLevel = response.body()!!
                val list: MutableList<MeasurementPair> = mutableListOf()

                if (response.stIndexLevel != null) {
                    list.add(
                        MeasurementPair(
                            MeasurementKey.ST_INDEX,
                            response.stIndexLevel.indexLevelName,
                            response.stIndexLevel.id,
                            null)
                    )
                }

                if (response.pm10IndexLevel != null) {
                    list.add(
                        MeasurementPair(
                            MeasurementKey.PM10,
                            response.pm10IndexLevel.indexLevelName,
                            response.pm10IndexLevel.id,
                            null
                        )
                    )
                }

                if (response.pm25IndexLevel != null) {
                    list.add(
                        MeasurementPair(
                            MeasurementKey.PM25,
                            response.pm25IndexLevel.indexLevelName,
                            response.pm25IndexLevel.id,
                            null
                        )
                    )
                }

                if (response.so2IndexLevel != null) {
                    list.add(
                        MeasurementPair(
                            MeasurementKey.SO2,
                            response.so2IndexLevel.indexLevelName,
                            response.so2IndexLevel.id,
                            null
                        )
                    )
                }

                if (response.no2IndexLevel != null) {
                    list.add(
                        MeasurementPair(
                            MeasurementKey.NO2,
                            response.no2IndexLevel.indexLevelName,
                            response.no2IndexLevel.id,
                            null
                        )
                    )
                }

                if (response.coIndexLevel != null) {
                    list.add(
                        MeasurementPair(
                            MeasurementKey.CO,
                            response.coIndexLevel.indexLevelName,
                            response.coIndexLevel.id,
                            null
                        )
                    )
                }

                if (response.o3IndexLevel != null) {
                    list.add(
                        MeasurementPair(
                            MeasurementKey.O3,
                            response.o3IndexLevel.indexLevelName,
                            response.o3IndexLevel.id,
                            null
                        )
                    )
                }

                if (response.c6h6IndexLevel != null) {
                    list.add(
                        MeasurementPair(
                            MeasurementKey.C6H6,
                            response.c6h6IndexLevel.indexLevelName,
                            response.c6h6IndexLevel.id,
                            null
                        )
                    )
                }
                measurements.postValue(list)
                loadSensorsFromStation(list)
            }

            override fun onFailure(call: Call<IndexLevel>, t: Throwable) {
                TODO("Not implemented yet")
            }
        })
    }

    fun loadSensorsFromStation(measurementList: List<MeasurementPair> ){
        val sensorList: MutableList<Int> = mutableListOf()

        val sensorsFromStationsData: Call<List<Sensor>> =
            ApiClient().getSensorsService().getSensor(stationId.toString())

        sensorsFromStationsData.enqueue(object : Callback<List<Sensor>> {
            override fun onResponse(call: Call<List<Sensor>>, response: Response<List<Sensor>>) {
                val response: List<Sensor> = response.body()!!
                response.forEach{
                    sensorList.add(it.id)
                }

                loadSensorsValues(sensorList, measurementList)
            }

            override fun onFailure(call: Call<List<Sensor>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun loadSensorsValues(sensorList: List<Int>, measurementList: List<MeasurementPair>){
        sensorList.forEach {
            val sensorValueData: Call<Values> = ApiClient().getSensorValuesService()
                .getSensorValues(it.toString())

            sensorValueData.enqueue(object : Callback<Values> {
                override fun onResponse(call: Call<Values>, response: Response<Values>) {
                    val values: Values = response.body()!!
                    val key = when(values.key) {
                        "NO2" -> MeasurementKey.NO2
                        "SO2" -> MeasurementKey.SO2
                        "PM10" -> MeasurementKey.PM10
                        "PM2.5" -> MeasurementKey.PM25
                        "C6H6" -> MeasurementKey.C6H6
                        "O3" -> MeasurementKey.O3
                        "CO" -> MeasurementKey.CO
                        else -> null
                    }
                    val lastValue = values.values.filter { value -> value.value != null }[0]
                    measurementList.forEach {
                        if(it.key == key){
                            it.measurementValue = lastValue.value
                        }
                    }

                    measurements.postValue(measurementList)
                }

                override fun onFailure(call: Call<Values>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }


    fun getMeasurements(): LiveData<List<MeasurementPair>> {
        return measurements
    }
}