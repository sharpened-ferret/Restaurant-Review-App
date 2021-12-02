package com.example.restaurantreviewapp.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.restaurantreviewapp.fragment.SettingsFragment
import com.example.restaurantreviewapp.fragment.MyReviewsFragment

class AccountTabAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(index: Int): Fragment {
        when (index) {
            0 -> return SettingsFragment()
            1 -> return MyReviewsFragment()
        }
        return SettingsFragment()
    }

    override fun getItemCount(): Int
    {
        return 2
    }
}