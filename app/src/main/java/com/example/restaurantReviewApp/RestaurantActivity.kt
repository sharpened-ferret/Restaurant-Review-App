package com.example.restaurantReviewApp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.INFO
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
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
    private lateinit var restaurantUid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        db = Firebase.firestore
        reviewList = arrayListOf()
        val toolbar = findViewById<Toolbar>(R.id.secondary_toolbar)

        val bundle = intent.extras
        val restaurantName = bundle?.getString("restaurant_name")
        restaurantUid = bundle?.getString("restaurant_uid").toString()
        toolbar.title = restaurantName
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<View>(R.id.review_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(this) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        reviewAdapter = ReviewAdapter(reviewList)
        recyclerView.adapter = reviewAdapter

        val restaurantDescription = findViewById<TextView>(R.id.restaurant_description)

        val docRef = db.collection("restaurants").document(restaurantUid)
        val source = Source.DEFAULT
        docRef.get(source).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                restaurantDescription.text = document?.get("description").toString()

            } else {
                Log.d(TAG, "Cached get failed: ", task.exception)
            }
        }



        val currentUser = FirebaseAuth.getInstance().currentUser

        // Hides the 'post review' section for unregistered users
        if (currentUser == null) {
            val addReviewBlock = findViewById<CardView>(R.id.add_review)
            addReviewBlock.visibility = View.INVISIBLE
        }
        // Populates any existing review data the current user has for this restaurant
        else {
            db.collection("reviews")
                .whereEqualTo("userId", currentUser.uid)
                .whereEqualTo("restaurantId", restaurantUid)
                .get(Source.DEFAULT)
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userReview = task.result
                    val reviewText = findViewById<EditText>(R.id.edit_review_text)
                    val rating = findViewById<EditText>(R.id.rating)
                    val latitude = findViewById<EditText>(R.id.latitude)
                    val longitude = findViewById<EditText>(R.id.longitude)
                    if (userReview!!.documents.isNotEmpty()) {
                        val addReviewLabel = findViewById<TextView>(R.id.add_review_label)
                        addReviewLabel.text = resources.getString(R.string.edit_review_label)
                        val reviewDoc = userReview.documents[0]
                        reviewText.setText(
                            reviewDoc.get("reviewText").toString()
                        )
                        rating.setText(
                            reviewDoc.get("rating").toString()
                        )
                        val geoPoint = reviewDoc.get("location") as GeoPoint
                        latitude.setText(
                            geoPoint.latitude.toString()
                        )
                        longitude.setText(
                            geoPoint.longitude.toString()
                        )

                        //TODO Handle loading existing review data
                    }
                }
                    Log.d("InsanityCheck:", currentUser.uid + ", " + restaurantUid)
            }
        }


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
            .whereEqualTo("restaurantId", restaurantUid)
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
                    //Calls to update the displayed reviews
                    reviewAdapter.notifyDataSetChanged()
                }

            })
    }

}