package com.example.airqualityandroid.ui.stationsDeprecated

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.airquality.data.station.Station
import com.example.airqualityandroid.R
import com.example.airqualityandroid.utils.MeasurementsIntentStarter

class StationsAdapter: RecyclerView.Adapter<StationsAdapter.ViewHolder>(), Filterable {

    private lateinit var context: Context
    var stations = mutableListOf<Station>()
    var stationsFilter = mutableListOf<Station>()

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

        val linearLayoutParent = parent.parent as ViewGroup
        val searchView = linearLayoutParent.getChildAt(0) as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Implement action on adapter")
                Log.d("tag", newText + "")
                return false
            }

        })

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stationViewModel = stationsFilter[position]
        holder.stationLocation.text = stationViewModel.stationName
        holder.stationCondition.text = stationViewModel.addressStreet

        holder.itemView.setOnClickListener{
            MeasurementsIntentStarter.startMeasurementsActivity(context, stationsFilter[position])
        }
    }

    override fun getItemCount() = stations.size

    @SuppressLint("NotifyDataSetChanged")
    fun setStationList(stations: List<Station>){
        this.stations = stations.toMutableList()
        this.stationsFilter = this.stations
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    stationsFilter = stations
                } else {
                    val resultList = mutableListOf<Station>()
                    stations.forEach {
                        val stationString = it.stationName + it.addressStreet + it.city
                        if(stationString.lowercase().contains(charSearch.lowercase())){
                            resultList.add(it)
                        }
                    }

                    stationsFilter = resultList
                }

                val filterResults = FilterResults()
                filterResults.values = stationsFilter
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                stationsFilter = results?.values as MutableList<Station>
                notifyDataSetChanged()
            }
        }
    }
}