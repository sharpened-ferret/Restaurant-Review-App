package com.example.restaurantReviewApp.fragments

import android.app.SearchManager
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantReviewApp.R
import com.example.restaurantReviewApp.adapters.RestaurantAdapter
import com.example.restaurantReviewApp.models.RestaurantModel
import com.example.restaurantReviewApp.models.ReviewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RestaurantsFragment : Fragment() {
    private lateinit var db : FirebaseFirestore
    private lateinit var restaurantList : ArrayList<RestaurantModel>
    private lateinit var restaurantAdapter : RestaurantAdapter
    lateinit var filter : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_restaurants, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Firebase.firestore
        restaurantList = arrayListOf()

        val recyclerView = view.findViewById<View>(R.id.restaurant_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(view.context) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        restaurantAdapter = RestaurantAdapter(restaurantList)
        recyclerView.adapter = restaurantAdapter
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
                            var restaurantModel = dc.document.toObject(RestaurantModel::class.java)
                            restaurantList.add(restaurantModel)
                        }
                    }

                    restaurantAdapter.notifyDataSetChanged()
                }

            })
    }
}