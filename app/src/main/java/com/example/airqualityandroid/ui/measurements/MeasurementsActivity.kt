package com.example.airqualityandroid.ui.measurements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.airqualityandroid.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

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

        //FIXME: prototype
        //Veryfy is stationId set in shared preferences: https://developer.android.com/training/data-storage/shared-preferences
        //If yes, set filled start
        //otherwise set border
        val favButtonView: FloatingActionButton = findViewById(R.id.favorite_button)
        var n = 0
        favButtonView.setOnClickListener {
            if(n%2 == 0)
                favButtonView.setImageResource(R.drawable.ic_baseline_star_24)
            else favButtonView.setImageResource(R.drawable.ic_baseline_star_border_24)
            n++
        }
    }
}