package com.example.airqualityandroid.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.airquality.api.ApiClient
import com.example.airquality.data.station.Station
import com.example.airqualityandroid.R
import com.example.airqualityandroid.utils.MeasurementsIntentStarter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback

class MapsFragment : Fragment() {
    private val defaultLocation = LatLng(51.9194, 19.1451)
    private var locationPermissionGranted = false
    private lateinit var googleMap: GoogleMap
    private var lastKnownLocation: LatLng? = null
    private val DEFAULT_ZOOM = 7.00f
    private val LOCATION_ZOOM = 14.00f
    private val TAG = "MapFragment"
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val onMapReadyCallback = OnMapReadyCallback { map ->
        googleMap = map
        styleMap()
        updateLocationUI()
        getDeviceLocation()
        drawStationMarkers()
        setUpMapListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getLocationPermission()
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(activity as Context)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.w("onViewCreated", "onViewCreated ")
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(onMapReadyCallback)
    }

    private fun setUpMapListeners(){
        googleMap.setOnInfoWindowClickListener {
            MeasurementsIntentStarter.startMeasurementsActivity(requireContext(), it.tag as Station)
        }
    }

    private fun styleMap() {
        val nightModeFlags = requireContext().resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> applyNightModeStyle()
        }
    }

    private fun applyNightModeStyle() {
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                requireContext(), R.raw.style_json));
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
                    val latLng = LatLng(station.gegrLat.toDouble(), station.gegrLon.toDouble())
                    val caption: String = station.stationName
                    val marker = googleMap.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title(caption)
                    )
                    marker?.tag = station
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
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        defaultLocation,
                        DEFAULT_ZOOM
                    )
                )
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        Log.w("getDeviceLocation", "getDeviceLocation ")
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation

                locationResult.addOnSuccessListener { location ->
                    if (location != null) {
                        googleMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    location.latitude,
                                    location.longitude
                                ), LOCATION_ZOOM
                            )
                        )
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        googleMap.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM)
                        )
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
}