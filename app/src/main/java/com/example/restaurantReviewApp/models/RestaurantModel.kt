package com.example.restaurantReviewApp.models

import com.google.firebase.firestore.GeoPoint

data class RestaurantModel (var name: String, var location : GeoPoint, var description : String, var uid : String) {
    constructor() : this("", GeoPoint(0.0,0.0), "", "")
}