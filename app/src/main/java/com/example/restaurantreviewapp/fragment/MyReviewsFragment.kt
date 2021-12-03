package com.example.restaurantreviewapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantreviewapp.R
import com.example.restaurantreviewapp.RestaurantModel
import com.example.restaurantreviewapp.adapter.RestaurantAdapter

class MyReviewsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_my_reviews, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val restaurantModelArrayList = populateList()

        val recyclerView = view.findViewById<View>(R.id.restaurant_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(view.context) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        val mAdapter = RestaurantAdapter(restaurantModelArrayList)
        recyclerView.adapter = mAdapter
    }

    private fun populateList() : ArrayList<RestaurantModel> {
        val list = ArrayList<RestaurantModel>()
        val nameList = arrayOf(R.string.new_york_pizza, R.string.monnis, R.string.turtle_bay, R.string.basekamp)
        val distanceList = arrayOf(0.2, 0.3, 1.2, 1.4)
        val numReviewsList = arrayOf(3, 0, 2, 8)

        for (i in 0..3) {
            val restaurant = RestaurantModel()
            restaurant.setName(getString(nameList[i]))
            restaurant.setDistance(distanceList[i])
            restaurant.setNumReviews(numReviewsList[i])

            list.add(restaurant)
        }
        return list
    }
}