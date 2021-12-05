package com.example.restaurantReviewApp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.INFO
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantReviewApp.adapters.RestaurantAdapter
import com.example.restaurantReviewApp.adapters.ReviewAdapter
import com.example.restaurantReviewApp.models.RestaurantModel
import com.example.restaurantReviewApp.models.ReviewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class RestaurantActivity : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    private lateinit var reviewList : ArrayList<ReviewModel>
    private lateinit var reviewAdapter : ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        db = Firebase.firestore
        reviewList = arrayListOf()
        val toolbar = findViewById<Toolbar>(R.id.secondary_toolbar)

        val bundle = intent.extras
        val restaurantName = bundle?.getString("restaurant_name")
        val restaurantUID = bundle?.getString("restaurant_uid").toString()
        toolbar.title = restaurantName
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<View>(R.id.review_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(this) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        reviewAdapter = ReviewAdapter(reviewList)
        recyclerView.adapter = reviewAdapter

        val restaurantDescription = findViewById<TextView>(R.id.restaurant_description)

        val docRef = db.collection("restaurants").document(restaurantUID)
        val source = Source.DEFAULT
        docRef.get(source).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                Log.d(TAG, "Cached document data: ${document?.data}")
                restaurantDescription.text = document?.get("description").toString()

            } else {
                Log.d(TAG, "Cached get failed: ", task.exception)
            }
        }


        val currentUser = FirebaseAuth.getInstance().currentUser?.displayName
        restaurantDescription.text = currentUser.toString()


        EventChangeListener()

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

    private fun EventChangeListener() {
        db.collection("reviews")
            .whereNotEqualTo("userId", FirebaseAuth.getInstance().currentUser?.uid)
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
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