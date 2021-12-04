package com.example.restaurantreviewapp

class ReviewModel {
    var restaurantName: String? = null
    private var distance: Double = 0.0
    private var numReviews: Int = 0

    fun getName() : String {
        return restaurantName.toString()
    }

    fun setName(name: String) {
        this.restaurantName = name
    }

    fun getDistance() : Double {
        return distance
    }

    fun setDistance(distance: Double) {
        this.distance = distance
    }

    fun getNumReviews() : Int {
        return numReviews
    }

    fun setNumReviews(reviews: Int) {
        this.numReviews = reviews
    }
}