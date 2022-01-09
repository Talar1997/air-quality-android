package com.example.airqualityandroid.ui.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.airquality.api.ApiClient
import com.example.airquality.data.station.Station
import com.example.airqualityandroid.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback

class MapsFragment : Fragment() {
    private val defaultLocation = LatLng(51.9194, 19.1451)
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        //TODO: get users LatLng
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 7.0F))

        //TODO: move it to ViewModel file
        val stationCall: Call<List<Station>> = ApiClient().getStationService().getAllStations()
        stationCall.enqueue(object : Callback<List<Station>> {
            override fun onFailure(call: Call<List<Station>>?, t: Throwable?) {
                println("fail")
            }

            override fun onResponse(
                call: Call<List<Station>>,
                response: retrofit2.Response<List<Station>>
            ) {
                val list: List<Station> = response.body()!!
                list.forEach { station ->

                    //TODO: add other caption and make somehow on click to open other activity with details
                    val latLng = LatLng(station.gegrLat.toDouble(),station.gegrLon.toDouble())
                    val caption: String = station.city.name
                    googleMap.addMarker(MarkerOptions().position(latLng).title(caption))
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}