package com.appfiza.foursquare.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/**
 * Created by Fayçal KADDOURI 🐈 on 12/2/2022.
 */

/**
 *  Convert location callback into Flow
 *  @param [context] Application context
 */
@SuppressLint("MissingPermission")
@ExperimentalCoroutinesApi
fun lastKnownLocation(context: Context) = callbackFlow {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            trySend(locationResult.lastLocation)
            fusedLocationClient.removeLocationUpdates(this)
        }
    }

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.requestLocationUpdates(
            LocationRequest.create(),
            locationCallback,
            Looper.getMainLooper()
        )
    } else {
        trySend(null)
    }
    awaitClose { fusedLocationClient.removeLocationUpdates(locationCallback) }
}