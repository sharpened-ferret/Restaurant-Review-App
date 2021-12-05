package com.example.restaurantReviewApp.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.restaurantReviewApp.*
import com.example.restaurantReviewApp.models.RestaurantModel
import com.google.android.material.snackbar.Snackbar

class RestaurantAdapter(private val restaurantModelArrayList: MutableList<RestaurantModel>) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.restaurant_row_layout, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = restaurantModelArrayList[position]

        holder.name.text = info.getName()
        holder.distance.text = info.getDistance().toString() + "km"
        holder.numReviews.text = info.getNumReviews().toString() + " Reviews"
    }

    override fun getItemCount(): Int {
        return restaurantModelArrayList.size
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
            val dataBundle = Bundle()
            dataBundle.putString("restaurant_name", name.toString())
            dataBundle.putString("restaurant_uid", "JUcBjoVBORKVkfFpA62O")
            val restaurantIntent = Intent(itemView.context, RestaurantActivity::class.java)
            restaurantIntent.putExtras(dataBundle)
            startActivity(itemView.context, restaurantIntent, dataBundle)
        }
    }
}