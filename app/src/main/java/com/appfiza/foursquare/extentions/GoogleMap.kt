package com.appfiza.foursquare.extentions

import com.google.android.gms.maps.GoogleMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */

/**
 *  Convert OnCameraMove callback into Flow
 */
@ExperimentalCoroutinesApi
fun GoogleMap.onCameraMove() = callbackFlow {
    setOnCameraMoveListener {
        trySend(Unit)
    }
    awaitClose { setOnCameraMoveListener(null) }
}
