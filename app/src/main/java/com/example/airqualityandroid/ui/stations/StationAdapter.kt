package com.example.airqualityandroid.ui.stations

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.airquality.data.station.Station
import com.example.airqualityandroid.R

class StationAdapter(private val stations: MutableList<StationViewModel>): RecyclerView.Adapter<StationAdapter.ViewHolder>() {

    private lateinit var context: Context

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
        holder.stationCondition.text = stationViewModel.stationDescription
        holder.itemView.setOnClickListener{
            Toast.makeText(context, stationViewModel.stationName, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = stations.size
}