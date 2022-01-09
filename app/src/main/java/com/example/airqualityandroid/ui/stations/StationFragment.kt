package com.example.airqualityandroid.ui.stations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airquality.data.station.Station
import com.example.airqualityandroid.R
import com.example.airqualityandroid.databinding.FragmentStationsBinding
import com.example.airqualityandroid.ui.stationsDeprecated.StationsAdapter

class StationFragment : Fragment() {

    private lateinit var stationViewModel: StationViewModel
    private var _binding: FragmentStationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        stationViewModel =
            ViewModelProvider(this).get(StationViewModel::class.java)

        val stationAdapter = StationsAdapter()

        stationViewModel.getStations().observe(viewLifecycleOwner, Observer<List<Station>>{ station ->
            stationAdapter.setStationList(station)
        })

        val view = inflater.inflate(R.layout.fragment_stations, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.stations_recycler_view_dashboard)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = stationAdapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}