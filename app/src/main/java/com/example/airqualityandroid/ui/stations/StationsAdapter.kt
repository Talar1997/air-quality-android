package com.example.airqualityandroid.ui.stationsDeprecated

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.airquality.data.station.Station
import com.example.airqualityandroid.R
import com.example.airqualityandroid.utils.MeasurementsIntentStarter

class StationsAdapter(): RecyclerView.Adapter<StationsAdapter.ViewHolder>() {

    private lateinit var context: Context
    var stations = mutableListOf<Station>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stationLocation: TextView
        val stationCondition: TextView

        init {
            stationLocation = view.findViewById(R.id.station_location)
            stationCondition = view.findViewById(R.id.station_condition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.station_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stationViewModel = stations[position]
        holder.stationLocation.text = stationViewModel.stationName
        holder.stationCondition.text = stationViewModel.addressStreet

        holder.itemView.setOnClickListener{
            MeasurementsIntentStarter.startMeasurementsActivity(context, stations[position])
        }
    }

    override fun getItemCount() = stations.size

    @SuppressLint("NotifyDataSetChanged")
    fun setStationList(stations: List<Station>){
        this.stations = stations.toMutableList()
        notifyDataSetChanged()
    }
}