package com.appfiza.foursquare.model

/**
 * Created by Fayçal KADDOURI 🐈 on 12/2/2022.
 */
data class Location(
    val address: String,
    val adminRegion: String,
    val country: String,
    val formattedAddress: String,
    val locality: String,
    val postcode: String,
    val region: String
)