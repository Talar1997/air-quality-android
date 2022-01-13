package com.example.airqualityandroid.utils

import com.example.airquality.data.station.Station
import android.content.Context
import android.content.Intent
import com.example.airqualityandroid.ui.measurements.MeasurementsActivity

class MeasurementsIntentStarter {
    companion object {
        fun startMeasurementsActivity(context: Context, station: Station) {
            val intent = Intent(context, MeasurementsActivity::class.java)
            intent.putExtra("StationId", station.id)
            intent.putExtra("StationName", station.stationName)
            intent.putExtra("StationAddress", station.addressStreet)
            context.startActivity(intent)
        }
    }
}