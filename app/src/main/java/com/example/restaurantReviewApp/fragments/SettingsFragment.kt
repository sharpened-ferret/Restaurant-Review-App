package com.example.restaurantReviewApp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.restaurantReviewApp.R
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_settings, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        val usernameText = view.findViewById<TextView>(R.id.username)
        usernameText.text = user?.displayName.toString()
        val emailText = view.findViewById<TextView>(R.id.email)
        emailText.text = user?.email.toString()
    }
}