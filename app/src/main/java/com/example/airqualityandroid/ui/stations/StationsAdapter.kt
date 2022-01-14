package com.example.airqualityandroid.ui.stationsDeprecated

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.airquality.data.station.Station
import com.example.airqualityandroid.R
import com.example.airqualityandroid.utils.MeasurementsIntentStarter

class StationsAdapter: RecyclerView.Adapter<StationsAdapter.ViewHolder>(), Filterable {

    private lateinit var context: Context
    var allStations = mutableListOf<Station>()
    var filteredStations = mutableListOf<Station>()

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

        setupAndStyleSearch(parent)

        return ViewHolder(view)
    }

    private fun setupAndStyleSearch(parent: ViewGroup){
        val linearLayoutParent = parent.parent as ViewGroup
        val searchView = linearLayoutParent.getChildAt(0) as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter.filter(newText)
                return false
            }
        })

        val searchIcon = searchView.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)

        val cancelIcon = searchView.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)

        val textView = searchView.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.WHITE)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stationViewModel = filteredStations[position]

        holder.stationLocation.text = stationViewModel.stationName
        holder.stationCondition.text = stationViewModel.addressStreet

        holder.itemView.setOnClickListener{
            MeasurementsIntentStarter.startMeasurementsActivity(context, stationViewModel)
        }
    }

    override fun getItemCount() = filteredStations.size

    @SuppressLint("NotifyDataSetChanged")
    fun setStationList(stations: List<Station>){
        this.allStations = stations.toMutableList()
        this.filteredStations = this.allStations
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredStations = allStations
                } else {
                    val resultList = mutableListOf<Station>()
                    allStations.forEach {
                        val stationString = it.stationName + it.addressStreet + it.city
                        if(stationString.lowercase().contains(charSearch.lowercase())){
                            resultList.add(it)
                        }
                    }

                    filteredStations = resultList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredStations
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredStations = results?.values as MutableList<Station>
                notifyDataSetChanged()
            }
        }
    }
}