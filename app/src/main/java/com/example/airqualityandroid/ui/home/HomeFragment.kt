package com.example.airqualityandroid.ui.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airqualityandroid.R
import com.example.airqualityandroid.databinding.FragmentHomeBinding
import com.example.airqualityandroid.ui.stations.StationsViewModel
import com.example.airqualityandroid.utils.MeasurementsIntentStarter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class HomeFragment : Fragment() {

    private lateinit var stationsViewModel: StationsViewModel
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Activity)
        homeViewModel.fusedLocationClient = fusedLocationClient

        val sharedPref = activity?.getSharedPreferences(R.string.SAVED_STATIONS.toString(), Context.MODE_PRIVATE)
        val savedStations = sharedPref?.getStringSet(R.string.SAVED_STATIONS.toString(), mutableSetOf())

        val listOfFavourites = mutableListOf<Int>()
        savedStations?.forEach{
            listOfFavourites.add(it.toInt())
        }
        homeViewModel.favStationsIds = listOfFavourites

        stationsViewModel =
            ViewModelProvider(this).get(StationsViewModel::class.java)

        val homeRecyclerViewAdapter = HomeRecyclerViewAdapter()

        stationsViewModel.getStations().observe(viewLifecycleOwner, { station ->
            homeViewModel.allStations = station
            homeViewModel.getNearestStation().observe(viewLifecycleOwner, { nearestStation ->
                val nearestStationView = view.findViewById<CardView>(R.id.nearest_item)
                nearestStationView.findViewById<TextView>(R.id.nearest_station_location).text = nearestStation.station.stationName
                nearestStationView.findViewById<TextView>(R.id.nearest_station_condition).text = nearestStation.station.addressStreet
                nearestStationView.findViewById<TextView>(R.id.nearest_station_index).text = nearestStation.index?.indexName

                nearestStationView.setOnClickListener{
                    MeasurementsIntentStarter.startMeasurementsActivity(requireContext(), nearestStation.station)
                }

            })

            homeViewModel.getFavouriteStations().observe(viewLifecycleOwner, { favoriteStations ->
                homeRecyclerViewAdapter.setFavStationsList(favoriteStations)
            })
        })


        val recyclerView = view.findViewById<RecyclerView>(R.id.fav_home_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = homeRecyclerViewAdapter


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}