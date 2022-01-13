package com.example.airqualityandroid.ui.measurements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.airqualityandroid.R

class MeasurementsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.measurements_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MeasurementsFragment.newInstance())
                .commitNow()
        }

        val stationId = intent.getIntExtra("StationId", -1)
        var stationName = intent.getStringExtra("StationName")
        if (stationName.isNullOrEmpty()) stationName = ""

        Log.d("Measurements activity", "$stationId , $stationName")
    }
}