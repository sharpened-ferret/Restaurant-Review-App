package com.example.restaurantReviewApp.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.restaurantReviewApp.fragments.MapFragment
import com.example.restaurantReviewApp.fragments.RestaurantsFragment

class MainTabAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(index: Int): Fragment {
        when (index) {
            0 -> return RestaurantsFragment()
            1 -> return MapFragment()
        }
        return RestaurantsFragment()
    }

    override fun getItemCount(): Int
    {
        return 2
    }
}