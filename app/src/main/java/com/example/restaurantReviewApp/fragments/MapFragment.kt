package com.example.restaurantReviewApp.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.restaurantReviewApp.R
import com.example.restaurantReviewApp.models.RestaurantModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MapFragment : Fragment(), OnMapReadyCallback  {
    private lateinit var googleMap: GoogleMap

    private lateinit var db : FirebaseFirestore

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
        db = Firebase.firestore


        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)


    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(swanseaLocation))

        EventChangeListener()
    }

    private fun EventChangeListener() {
        db.collection("restaurants")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("FireStore Recycler Error", error.message.toString())
                        return
                    }
                    for (dc : DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            //var restaurantModel = dc.document.toObject(RestaurantModel::class.java)
                            var location = dc.document.get("location") as GeoPoint
                            var latLng = LatLng(location.latitude, location.longitude)
                            var name = dc.document.get("name") as String
                            googleMap.addMarker(MarkerOptions().position(latLng).title(name))
                        }
                    }
                }

            })
    }
}