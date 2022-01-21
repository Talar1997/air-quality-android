package com.example.airqualityandroid.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.airqualityandroid.R
import com.example.airqualityandroid.data.StationWithIndex
import com.example.airqualityandroid.utils.MeasurementsIntentStarter

class HomeRecyclerViewAdapter: RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {
    private lateinit var context: Context
    var favStations = mutableListOf<StationWithIndex>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stationName: TextView
        val stationDescription: TextView
        val stationIndex: TextView

        init {
            stationName = view.findViewById(R.id.fav_station_location)
            stationDescription = view.findViewById(R.id.fav_station_description)
            stationIndex = view.findViewById(R.id.fav_station_index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.home_item_fav, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stationViewModel = favStations[position]

        holder.stationName.text = stationViewModel.station.stationName
        holder.stationDescription.text = stationViewModel.station.addressStreet
        holder.stationIndex.text = context.getString(R.string.index_name) + " " + stationViewModel.index?.indexName

        holder.itemView.setOnClickListener{
            MeasurementsIntentStarter.startMeasurementsActivity(context, stationViewModel.station)
        }
    }

    override fun getItemCount() = favStations.size

    @SuppressLint("NotifyDataSetChanged")
    fun setFavStationsList(stations: List<StationWithIndex>){
        this.favStations = stations.toMutableList()
        notifyDataSetChanged()
    }
}