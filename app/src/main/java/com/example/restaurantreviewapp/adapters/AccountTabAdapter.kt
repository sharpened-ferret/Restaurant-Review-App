package com.example.restaurantreviewapp.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.restaurantreviewapp.fragments.SettingsFragment
import com.example.restaurantreviewapp.fragments.MyReviewsFragment

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