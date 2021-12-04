package com.example.restaurantReviewApp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantReviewApp.R
import com.example.restaurantReviewApp.adapters.ReviewAdapter
import com.example.restaurantReviewApp.models.ReviewModel

class MyReviewsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_my_reviews, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reviewModelArrayList = populateList()

        val recyclerView = view.findViewById<View>(R.id.review_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(view.context) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        val mAdapter = ReviewAdapter(reviewModelArrayList)
        recyclerView.adapter = mAdapter
    }

    private fun populateList() : ArrayList<ReviewModel> {
        val list = ArrayList<ReviewModel>()
        val nameList = arrayOf(R.string.new_york_pizza, R.string.monnis, R.string.turtle_bay, R.string.basekamp)
        val distanceList = arrayOf(0.2, 0.3, 1.2, 1.4)
        val numReviewsList = arrayOf(3, 0, 2, 8)

        for (i in 0..3) {
            val review = ReviewModel()
            review.setUsername(getString(nameList[i]))
            review.setReviewText(distanceList[i].toString())
            review.setRating(numReviewsList[i])
            review.setLocation("New York Pizza")
            review.setImage(0)

            list.add(review)
        }
        return list
    }
}