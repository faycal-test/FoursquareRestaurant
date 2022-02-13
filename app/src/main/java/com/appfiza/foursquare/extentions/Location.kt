package com.appfiza.foursquare.extentions

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */

/**
 *  Extension to get south/west points in string
 */
fun LatLngBounds.swLatLng() = southwest.latitude.toString() + "," + southwest.longitude.toString()

/**
 *  Extension to get north/east points in string
 */
fun LatLngBounds.neLatLng() = northeast.latitude.toString() + "," + northeast.longitude.toString()

/**
 * Convert latitude and longitude into a LatLng object
 */
fun Location.toLatLng(): LatLng = LatLng(latitude, longitude)
