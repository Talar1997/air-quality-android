package com.example.airqualityandroid.utils

import android.content.Context
import com.example.airqualityandroid.R
import com.example.airqualityandroid.api.MeasurementKey

class MeasurementResolver {
    companion object {
        fun resolveMeasurementName(context: Context, key: MeasurementKey): String {
            return when (key) {
                MeasurementKey.ST_INDEX -> context.getString(R.string.ST_INDEX)
                MeasurementKey.CO -> context.getString(R.string.CO)
                MeasurementKey.NO2 -> context.getString(R.string.NO2)
                MeasurementKey.O3 -> context.getString(R.string.O3)
                MeasurementKey.SO2 -> context.getString(R.string.SO2)
                MeasurementKey.PM25 -> context.getString(R.string.PM25)
                MeasurementKey.PM10 -> context.getString(R.string.PM10)
                MeasurementKey.C6H6 -> context.getString(R.string.C6H6)
            }
        }

        fun resolveMeasurementDescription(context: Context, key: MeasurementKey): String{
            return when (key) {
                MeasurementKey.ST_INDEX -> context.getString(R.string.ST_INDEX_DESCRIPTION)
                MeasurementKey.CO -> context.getString(R.string.CO_DESCRIPTION)
                MeasurementKey.NO2 -> context.getString(R.string.NO2_DESCRIPTION)
                MeasurementKey.O3 -> context.getString(R.string.O3_DESCRIPTION)
                MeasurementKey.SO2 -> context.getString(R.string.SO2_DESCRIPTION)
                MeasurementKey.PM25 -> context.getString(R.string.PM25_DESCRIPTION)
                MeasurementKey.PM10 -> context.getString(R.string.PM10_DESCRIPTION)
                MeasurementKey.C6H6 -> context.getString(R.string.C6H6_DESCRIPTION)
            }
        }
    }
}