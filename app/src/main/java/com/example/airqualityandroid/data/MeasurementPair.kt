package com.example.airquality.data

import com.example.airqualityandroid.api.MeasurementKey

data class MeasurementPair(val key: MeasurementKey, val indexName: String, val index: Int, var measurementValue: Double?)
