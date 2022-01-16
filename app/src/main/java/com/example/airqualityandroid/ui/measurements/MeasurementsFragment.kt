package com.example.airqualityandroid.ui.measurements

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airqualityandroid.R
import com.example.airqualityandroid.databinding.FragmentStationsBinding

class MeasurementsFragment : Fragment() {
    private lateinit var viewModel: MeasurementsViewModel
    private var _binding: FragmentStationsBinding? = null
    private val binding get() = _binding!!
    private var stationId = -1
    private var stationName = ""

    companion object {
        fun newInstance() = MeasurementsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(MeasurementsViewModel::class.java)
        viewModel.stationId = this.stationId

        val measurementsAdapter = MeasurementsAdapter()

        viewModel.getMeasurements().observe(viewLifecycleOwner, { measurementPairs ->
            measurementsAdapter.setMeasurementPairsList(measurementPairs)
        })

        val view = inflater.inflate(R.layout.measurements_fragment, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.measurements_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = measurementsAdapter

        val stationNameTextView = view.findViewById<TextView>(R.id.measurement_station_name)
        stationNameTextView.text = stationName

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun setStation(stationId: Int, stationName: String){
        this.stationId = stationId
        this.stationName = stationName
    }

}