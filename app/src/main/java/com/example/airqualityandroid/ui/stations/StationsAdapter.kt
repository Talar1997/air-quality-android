package com.example.airqualityandroid.ui.stationsDeprecated

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.airquality.data.station.Station
import com.example.airqualityandroid.R

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

        //TODO: when onClick on station, move to other context with detailed data for station
        // example:
        /*holder.itemView.setOnClickListener{
            val intent = Intent(context, StationActivity::class.java)   //Create activity for station details
            intent.putExtra("StationId", list[position].id)             //list here equals for stations
            intent.putExtra("StationName", list[position].stationName)
            intent.putExtra("StationAddress", list[position].addressStreet)
            context.startActivity(intent)
        }*/

        holder.itemView.setOnClickListener{
            Toast.makeText(context, stationViewModel.stationName, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = stations.size

    fun setStationList(stations: List<Station>){
        this.stations = stations.toMutableList()
        notifyDataSetChanged()
    }
}