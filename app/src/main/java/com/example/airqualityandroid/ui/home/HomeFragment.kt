package com.example.airqualityandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.airquality.api.ApiClient
import com.example.airquality.data.station.Station
import com.example.airqualityandroid.R
import com.example.airqualityandroid.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val stationList: Call<List<Station>> = ApiClient().getStationService().getAllStations()
        stationList.enqueue(object : Callback<List<Station>> {
            override fun onFailure(call: Call<List<Station>>?, t: Throwable?) {
                println("fail")
            }

            override fun onResponse(
                call: Call<List<Station>>,
                response: retrofit2.Response<List<Station>>
            ) {
                val list: List<Station> = response.body()!!
            }
        })



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}