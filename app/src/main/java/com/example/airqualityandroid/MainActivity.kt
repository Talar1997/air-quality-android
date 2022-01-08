package com.example.airqualityandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airquality.api.ApiClient
import com.example.airquality.data.station.Station
import com.example.airqualityandroid.databinding.ActivityMainBinding
import com.example.airqualityandroid.ui.stations.StationAdapter
import com.example.airqualityandroid.ui.stations.StationViewModel
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var stationAdapter: StationAdapter
    private val stationDataList: MutableList<StationViewModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //adapter config
        stationAdapter = StationAdapter(stationDataList)

        //recyclerview config
        val recyclerview = findViewById<RecyclerView>(R.id.stations_recycler_view)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = stationAdapter

        val stationApiCall: Call<List<Station>> = ApiClient().getStationService().getAllStations()
        stationApiCall.enqueue(object : Callback<List<Station>> {
            override fun onFailure(call: Call<List<Station>>?, t: Throwable?) {
//                Toast.makeText(this, "Failed to load data from API", Toast.LENGTH_LONG).show()
                TODO("Not implemented yet")
            }

            override fun onResponse(
                call: Call<List<Station>>,
                response: retrofit2.Response<List<Station>>
            ) {
                val list: List<Station> = response.body()!!
                list.forEach{
                    stationDataList.add(StationViewModel(it.stationName, it.addressStreet))
                }
                stationAdapter.notifyDataSetChanged()
            }
        })

//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_map
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }
}