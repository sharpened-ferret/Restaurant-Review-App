package com.example.restaurantreviewapp.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.restaurantreviewapp.fragments.MapFragment
import com.example.restaurantreviewapp.fragments.RestaurantsFragment

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