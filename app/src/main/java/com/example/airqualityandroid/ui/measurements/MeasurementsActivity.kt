package com.example.airqualityandroid.ui.measurements

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.airqualityandroid.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
            fragment.setStation(stationId, stationName)

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }

        val favButtonView: FloatingActionButton = findViewById(R.id.favorite_button)
        val sharedPref = this.getSharedPreferences(R.string.SAVED_STATIONS.toString(), Context.MODE_PRIVATE)
        val savedStations = sharedPref.getStringSet(R.string.SAVED_STATIONS.toString(), mutableSetOf())

        if(savedStations?.contains(stationId.toString()) == true){
            favButtonView.setImageResource(R.drawable.ic_baseline_star_24)
        } else {
            favButtonView.setImageResource(R.drawable.ic_baseline_star_border_24)
        }

        favButtonView.setOnClickListener {
            setFavButtonListener(it, stationId)
        }
    }

    private fun setFavButtonListener(it: View, stationId: Int){
        val button = it as FloatingActionButton
        val sharedPreferences = this.getSharedPreferences(R.string.SAVED_STATIONS.toString(), Context.MODE_PRIVATE)
        val stationsInSharedPreferences = sharedPreferences.getStringSet(R.string.SAVED_STATIONS.toString(), mutableSetOf())
        val stations = stationsInSharedPreferences?.toMutableSet()

        if(stations?.contains(stationId.toString()) == true){
            with (sharedPreferences.edit()) {
                stations.remove(stationId.toString())
                putStringSet(R.string.SAVED_STATIONS.toString(), stations)
                apply()
            }
            button.setImageResource(R.drawable.ic_baseline_star_border_24)

        } else {
            with (sharedPreferences.edit()) {
                stations?.add(stationId.toString())
                putStringSet(R.string.SAVED_STATIONS.toString(), stations)
                apply()
            }
            button.setImageResource(R.drawable.ic_baseline_star_24)
        }
    }
}