package com.example.restaurantReviewApp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.restaurantReviewApp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class MapFragment : Fragment(), OnMapReadyCallback  {
    private lateinit var googleMap: GoogleMap

    private val swanseaLocation: CameraPosition = CameraPosition.Builder().
    target(LatLng(51.621441, -3.943646))
        .zoom(12.5f)
        .bearing(0f)
        .tilt(0f)
        .build()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_map, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapView = view.findViewById<MapView>(R.id.map_view)


        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)


    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(swanseaLocation))
    }

}