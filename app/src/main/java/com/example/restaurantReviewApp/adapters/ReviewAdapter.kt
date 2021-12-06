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

class ReviewAdapter(private val reviewModelArrayList: MutableList<ReviewModel>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.review_row_layout, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = reviewModelArrayList[position]

        val latVal = info.location?.latitude.toString()
        val longVal = info.location?.longitude.toString()

        holder.username.text = info.username.toString()
        holder.reviewText.text = info.reviewText.toString()
        holder.rating.text = info.rating.toString()
        holder.longitude.text = longVal
        holder.latitude.text = latVal
        holder.image.setImageURI(info.imageUri?.toUri())
    }

    override fun getItemCount(): Int {
        return reviewModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var username = itemView.findViewById<View>(R.id.username) as TextView
        var reviewText = itemView.findViewById<View>(R.id.review_text) as TextView
        var rating = itemView.findViewById<View>(R.id.rating) as TextView
        var latitude = itemView.findViewById<View>(R.id.latitude) as TextView
        var longitude = itemView.findViewById<View>(R.id.longitude) as TextView
        var image = itemView.findViewById<View>(R.id.image) as ImageView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
//            val name = name.text
//            val distance = distance.text
//            val numReviews = numReviews.text
//            val snackbar = Snackbar.make(v, "$name $distance $numReviews", Snackbar.LENGTH_LONG)
//            snackbar.show()
        }
    }
}