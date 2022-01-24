package com.example.airqualityandroid.data.station

import com.example.airqualityandroid.data.measurement.MeasurementPair
import com.example.airquality.data.station.Station

data class StationWithIndex(val station: Station, val index: MeasurementPair?)

