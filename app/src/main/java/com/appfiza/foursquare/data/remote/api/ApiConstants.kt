package com.appfiza.foursquare.data.remote.api

/**
 * Created by Fayçal KADDOURI 🐈
 */
object ApiConstants {
    const val TIMEOUT_IN_SECONDS: Long = 30
    /**
     * ID that refers to Foursquare restaurants @see [Categories](https://developer.foursquare.com/docs/categories)
     */
    const val RESTAURANT_ID = "13065"

    /**
     *  Number of places we want to have per request @see [Pagination](https://developer.foursquare.com/reference/pagination)
     *
     */
    const val LIMIT_RESULTS = 50

    /**
     *  API Key to authenticate API calls @see [How to generate an API KEY](https://developer.foursquare.com/docs/manage-api-keys)
     */
    const val API_KEY = "fsq3Dvtm2aCMZDrKT/we4bCJFNTEhDjiUZksLZ5B9xv2BVA="

    const val BASE_URL = "https://api.foursquare.com/v3/"
}