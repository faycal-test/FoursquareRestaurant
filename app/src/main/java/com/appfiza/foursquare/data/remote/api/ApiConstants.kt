package com.appfiza.foursquare.data.remote.api

/**
 * Created by Fayçal KADDOURI 🐈
 */
object ApiConstants {
    const val TIMEOUT_IN_SECONDS: Long = 30
    /**
     * ID that refers to Foursquare restaurants
     * https://developer.foursquare.com/docs/categories
     */
    const val RESTAURANT_ID = "13065"

    /**
     *  Number of places we want to have per request
     */
    const val LIMIT_RESULTS = 50
    const val API_KEY = "fsq3Dvtm2aCMZDrKT/we4bCJFNTEhDjiUZksLZ5B9xv2BVA="
    const val BASE_URL = "https://api.foursquare.com/v3/"
}