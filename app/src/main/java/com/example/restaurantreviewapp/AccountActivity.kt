package com.example.restaurantreviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val toolbar = findViewById<Toolbar>(R.id.secondary_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        val usernameText = findViewById<TextView>(R.id.username)
        usernameText.setText("Bobby Droptable")
        val emailText = findViewById<TextView>(R.id.email)
        emailText.setText("bobby@example.com")
        val passwordText = findViewById<TextView>(R.id.password)
        passwordText.setText("**********")



    }

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


}