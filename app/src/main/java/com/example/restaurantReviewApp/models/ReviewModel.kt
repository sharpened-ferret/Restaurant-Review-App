package com.example.restaurantReviewApp.models

class ReviewModel {
    private var username: String? = null
    private var reviewText: String? = null
    private var rating: Int = 0
    private var location: String? = null
    private var image: Int = 0

    fun getUsername() : String {
        return username.toString()
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun getReviewText() : String {
        return reviewText.toString()
    }

    fun setReviewText(reviewText : String) {
        this.reviewText = reviewText
    }

    fun getRating() : Int {
        return rating
    }

    fun setRating(rating : Int) {
        this.rating = rating
    }

    fun getLocation() : String {
        return location.toString()
    }

    fun setLocation(location : String) {
        this.location = location
    }

    fun getImage() : Int {
        return image
    }

    fun setImage(image : Int) {
        this.image = image
    }
}