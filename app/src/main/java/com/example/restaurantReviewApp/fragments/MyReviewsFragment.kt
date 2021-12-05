package com.example.restaurantReviewApp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantReviewApp.R
import com.example.restaurantReviewApp.adapters.ReviewAdapter
import com.example.restaurantReviewApp.models.ReviewModel
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyReviewsFragment : Fragment() {
    private lateinit var db : FirebaseFirestore
    private lateinit var reviewList : ArrayList<ReviewModel>
    private lateinit var reviewAdapter : ReviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_my_reviews, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val reviewModelArrayList = populateList()

        db = Firebase.firestore
        reviewList = arrayListOf()

        val recyclerView = view.findViewById<View>(R.id.review_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(view.context) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        reviewAdapter = ReviewAdapter(reviewList)
        recyclerView.adapter = reviewAdapter

        EventChangeListener()
    }

//    private fun populateList() : ArrayList<ReviewModel> {
//        val list = ArrayList<ReviewModel>()
//        val nameList = arrayOf(R.string.new_york_pizza, R.string.monnis, R.string.turtle_bay, R.string.basekamp)
//        val distanceList = arrayOf(0.2, 0.3, 1.2, 1.4)
//        val numReviewsList = arrayOf(3, 0, 2, 8)
//
//        for (i in 0..3) {
//            val review = ReviewModel()
//            review.setUsername(getString(nameList[i]))
//            review.setReviewText(distanceList[i].toString())
//            review.setRating(numReviewsList[i])
//            review.setLocation("New York Pizza")
//            review.setImage(0)
//
//            list.add(review)
//        }
//        return list
//    }

    private fun EventChangeListener() {
        db.collection("reviews")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("FireStore Recycler Error", error.message.toString())
                        return
                    }
                    for (dc : DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            reviewList.add(dc.document.toObject(ReviewModel::class.java))
                        }
                    }

                    reviewAdapter.notifyDataSetChanged()
                }

            })
    }
}