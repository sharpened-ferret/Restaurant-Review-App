package com.example.restaurantreviewapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.restaurantreviewapp.adapter.TabAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainToolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(mainToolbar)

        val imageModelArrayList = populateList()

        val recyclerView = findViewById<View>(R.id.restaurant_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(this) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        val mAdapter = RestaurantAdapter(imageModelArrayList)
        recyclerView.adapter = mAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        val tabTitles = resources.getStringArray(R.array.mainTabTitles)
        viewPager.adapter = TabAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = tabTitles[0]
                1 -> tab.text = tabTitles[1]
            }
        }.attach()

//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                if (tab != null) {
//                    when (tab.id) {
//                        R.id.restaurants_tab -> {
//                            viewPager.visibility = View.INVISIBLE
//                        }
//
//                        R.id.map_tab -> {
//                            tab.text = "Test"
//                        }
//                    }
//                }
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                // Handle tab reselect
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                // Handle tab unselect
//            }
//        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.toolbar_layout), menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val myView = findViewById<View>(R.id.main_toolbar)
        when (item.itemId) {
            R.id.refresh -> {
                finish()
                overridePendingTransition(0, 0);
                startActivity(intent)
                overridePendingTransition(0, 0);
                return true
            }
            R.id.account -> {
                val accountIntent = Intent(this, AccountActivity::class.java)
                startActivity(accountIntent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun populateList() : ArrayList<RestaurantModel> {
        val list = ArrayList<RestaurantModel>()
        val nameList = arrayOf(R.string.new_york_pizza, R.string.monnis, R.string.turtle_bay, R.string.basekamp)
        val distanceList = arrayOf(0.2, 0.3, 1.2, 1.4)
        val numReviewsList = arrayOf(3, 0, 2, 8)

        for (i in 0..3) {
            val restaurant = RestaurantModel()
            restaurant.setName(getString(nameList[i]))
            restaurant.setDistance(distanceList[i])
            restaurant.setNumReviews(numReviewsList[i])

            list.add(restaurant)
        }
        return list
    }

}