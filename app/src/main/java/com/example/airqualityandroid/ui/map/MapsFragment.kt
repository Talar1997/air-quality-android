package com.example.airqualityandroid.ui.map

import android.Manifest
import android.annotation.SuppressLint
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.airquality.api.ApiClient
import com.example.airquality.data.station.Station
import com.example.airqualityandroid.R
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback

class MapsFragment : Fragment() {
    private val defaultLocation = LatLng(51.9194, 19.1451)
    private var locationPermissionGranted = false
    private lateinit var googleMap: GoogleMap
    private var lastKnownLocation: LatLng? = null
    private val DEFAULT_ZOOM = 7.00f
    private val TAG = "MapFragment"
    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    private val onMapReadyCallback = OnMapReadyCallback { map ->
        googleMap = map
        updateLocationUI()
        getDeviceLocation()
        drawStationMarkers()
    }

    private fun drawStationMarkers() {
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

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        try {
            if (locationPermissionGranted) {
                googleMap.isMyLocationEnabled = true
                googleMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                googleMap.isMyLocationEnabled = false
                googleMap.uiSettings.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM))
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation

                //FIXME: figure out error
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude), DEFAULT_ZOOM))
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        googleMap.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM))
                        googleMap.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getLocationPermission() {
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            locationPermissionGranted = isGranted
        }

        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
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
        mapFragment?.getMapAsync(onMapReadyCallback)
    }

}