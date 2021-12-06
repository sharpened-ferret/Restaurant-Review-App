package com.example.restaurantReviewApp.adapters

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.example.restaurantReviewApp.*
import com.example.restaurantReviewApp.models.ReviewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyReviewAdapter(private val reviewModelArrayList: MutableList<ReviewModel>) : RecyclerView.Adapter<MyReviewAdapter.ViewHolder>() {
    private lateinit var db : FirebaseFirestore

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.review_row_layout, parent, false)
        db = Firebase.firestore

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = reviewModelArrayList[position]

        val docRef = db.collection("restaurants").document(info.restaurantId)
        val source = Source.DEFAULT
        docRef.get(source).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                holder.restaurantName.text = document?.get("name").toString()
            } else {
                holder.restaurantName.text = ""
            }
        }

        val latVal = info.location?.latitude.toString()
        val longVal = info.location?.longitude.toString()

        holder.reviewText.text = info.reviewText.toString()
        holder.rating.text = info.rating.toString()
        holder.longitude.text = longVal
        holder.latitude.text = latVal
        holder.image.setImageURI(info.imageUri?.toUri())
    }

    override fun getItemCount(): Int {
        return reviewModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var restaurantName = itemView.findViewById<View>(R.id.username) as TextView
        var reviewText = itemView.findViewById<View>(R.id.review_text) as TextView
        var rating = itemView.findViewById<View>(R.id.rating) as TextView
        var latitude = itemView.findViewById<View>(R.id.latitude) as TextView
        var longitude = itemView.findViewById<View>(R.id.longitude) as TextView
        var image = itemView.findViewById<View>(R.id.image) as ImageView
    }
}