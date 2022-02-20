package com.appfiza.foursquare.model

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
data class Place(
    val id: String,
    val name: String,
    val geocodes: Geocodes,
    val location: Location,
    val photos: List<PlacePhotos> = emptyList()
) {
    /**
     * Convert latitude and longitude into a LatLng object
     */
    fun toLatLng(): LatLng = LatLng(geocodes.latitude, geocodes.longitude)
}
