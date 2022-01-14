package com.example.airqualityandroid.ui.measurements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.airquality.api.ApiClient
import com.example.airquality.data.MeasurementPair
import com.example.airquality.data.index.IndexLevel
import com.example.airqualityandroid.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MeasurementsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.measurements_activity)
        title = getString(R.string.title_measurements)

        val stationId = intent.getIntExtra("StationId", -1)
        var stationName = intent.getStringExtra("StationName")
        if (stationName.isNullOrEmpty()) stationName = ""

        if (savedInstanceState == null) {
            val fragment = MeasurementsFragment.newInstance()
            fragment.setStation(stationId, stationName) //FIXME: where it should be passed? Adapter/Fragment?

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }

    }
}