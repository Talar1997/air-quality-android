package com.example.airqualityandroid.data

import com.example.airquality.data.MeasurementPair
import com.example.airquality.data.station.Station

data class StationWithIndex(val station: Station, val index: MeasurementPair?)

