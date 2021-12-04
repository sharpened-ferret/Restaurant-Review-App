package com.example.restaurantreviewapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.restaurantreviewapp.*
import com.google.android.material.snackbar.Snackbar

class ReviewAdapter(private val imageModelArrayList: MutableList<ReviewModel>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.review_row_layout, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]

        holder.name.text = info.getName()
        holder.distance.text = info.getDistance().toString() + "km"
        holder.numReviews.text = info.getNumReviews().toString() + " Reviews"
    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var name = itemView.findViewById<View>(R.id.name) as TextView
        var distance = itemView.findViewById<View>(R.id.distance) as TextView
        var numReviews = itemView.findViewById<View>(R.id.numReviews) as TextView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val name = name.text
            val distance = distance.text
            val numReviews = numReviews.text
            val snackbar = Snackbar.make(v, "$name $distance $numReviews", Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }
}