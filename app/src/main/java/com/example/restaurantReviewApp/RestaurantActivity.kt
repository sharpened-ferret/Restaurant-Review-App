package com.example.restaurantReviewApp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantReviewApp.adapters.ReviewAdapter
import com.example.restaurantReviewApp.models.ReviewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class RestaurantActivity : AppCompatActivity() {
    //private lateinit val mStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        val toolbar = findViewById<Toolbar>(R.id.secondary_toolbar)

        val bundle = intent.extras
        val restaurantName = bundle?.getString("restaurant_name")
        val restaurantUID = bundle?.getString("restaurant_uid").toString()
        toolbar.title = restaurantName
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val reviewModelArrayList = populateList()
        val recyclerView = findViewById<View>(R.id.review_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(this) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        val mAdapter = ReviewAdapter(reviewModelArrayList)
        recyclerView.adapter = mAdapter

        val db = Firebase.firestore
        val restaurantDescription = findViewById<TextView>(R.id.restaurant_description)
        val currentUser = FirebaseAuth.getInstance().currentUser?.displayName
        restaurantDescription.text = currentUser.toString()

        val docRef = db.collection("restaurants").document(restaurantUID)

        val source = Source.DEFAULT


        docRef.get(source).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val document = task.result
                Log.d(TAG, "Cached document data: ${document?.data}")
                restaurantDescription.text = document?.get("description").toString()

            } else {
                Log.d(TAG, "Cached get failed: ", task.exception)
            }
        }


    }

    private fun populateList(): MutableList<ReviewModel> {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.secondary_toolbar_layout), menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun uploadFile() {
        //val uploadRef = mStorage
    }

}