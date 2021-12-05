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

        //val reviewModelArrayList = populateList(restaurantUID)
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

//    private fun populateList(restaurantUID : String): MutableList<ReviewModel> {
//        val list = ArrayList<ReviewModel>()
//        val nameList = ArrayList<String>()
//        val reviewTextList = ArrayList<String>()
//        val ratingList = ArrayList<Int>()
//        db.collection("reviews")
//            .get(Source.DEFAULT)
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d("DocumentReturn: ", "${document.id} => ${document.data}")
////                    nameList.add(document["user_id"] as String)
////                    Log.d("TestDocumentReturn", document["user_id"] as String)
////                    reviewTextList.add(document["review_text"] as String)
////                    ratingList.add((document["rating"] as Long).toInt())
//
//                    val reviewModel = ReviewModel()
//                    reviewModel.setUsername(document["user_id"] as String)
//                    reviewModel.setReviewText(document["review_text"] as String)
//                    reviewModel.setRating((document["rating"] as Long).toInt())
//                    reviewModel.setLocation("New York Pizza")
//                    list.add(reviewModel)
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
////        if (nameList.size > 0) {
////            for (i in 0 until nameList.size) {
////                val review = ReviewModel()
////                review.setUsername(nameList[i])
////                review.setReviewText(reviewTextList[i])
////                review.setRating(ratingList[i])
////                review.setLocation("New York Pizza")
////                //review.setImage(0)
////
////                list.add(review)
////            }
////        }
//        Log.d(TAG, "NameList: " + nameList.size)
//        Log.d(TAG, "RecyclerListSize: " + list.size)
//        return list
//    }

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