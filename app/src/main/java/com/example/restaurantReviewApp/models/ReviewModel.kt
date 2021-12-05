package com.example.restaurantReviewApp.models

import com.google.firebase.firestore.GeoPoint


data class ReviewModel(var userId : String,
                       var username : String,
                       var restaurantId : String,
                       var location : GeoPoint?,
                       var reviewText : String,
                       var rating : Long,
                       var imageUri: String?
) {
    constructor() : this("", "", "", null, "", 0, null)
}
