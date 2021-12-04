package com.example.restaurantreviewapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.restaurantreviewapp.R

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_settings, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameText = view.findViewById<TextView>(R.id.username)
        usernameText.text = "Bobby Droptable"
        val emailText = view.findViewById<TextView>(R.id.email)
        emailText.text = "bobby@example.com"
        val passwordText = view.findViewById<TextView>(R.id.password)
        passwordText.text = "**********"
    }
}