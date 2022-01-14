package com.example.airqualityandroid.ui.measurements

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.airqualityandroid.R

class MeasurementsFragment : Fragment() {

    fun setStation(stationId: Int, stationName: String){
        Log.d("MeasurementsFragment, setStation", "$stationId, $stationName")
    }

    companion object {
        fun newInstance() = MeasurementsFragment()
    }

    private lateinit var viewModel: MeasurementsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.measurements_fragment, container, false)

//        val stationIndexData: Call<IndexLevel> =
//            ApiClient().getStationIndexService().getStationIndex(stationId.toString())
//
//        //TODO: move to ViewModel file
//        stationIndexData.enqueue(object : Callback<IndexLevel> {
//            override fun onResponse(call: Call<IndexLevel>, response: Response<IndexLevel>) {
//                val response: IndexLevel = response.body()!!
//                TODO("Not implemented yet")
//            }
//
//            override fun onFailure(call: Call<IndexLevel>, t: Throwable) {
//                println("failed")
//            }
//        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MeasurementsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}