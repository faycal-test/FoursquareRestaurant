package com.appfiza.foursquare.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.appfiza.foursquare.data.remote.api.mappers.GeocodesDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.LocationDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.PlaceDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.PlacePhotosDTOMapper
import com.appfiza.foursquare.data.remote.api.model.PlaceDTO
import com.appfiza.foursquare.data.remote.api.model.PlacePhotosDTO
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by Fayçal KADDOURI 🐈
 * [Link to article](https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04)
 */

/**
 *  Map a PlacePhotoDTO to PlacePhoto
 */
fun PlacePhotosDTO.toDomain() = PlacePhotosDTOMapper().mapToDomain(this)

/**
 *  Map a PlaceDTO to Place
 */
fun PlaceDTO.toDomain() = PlaceDTOMapper(GeocodesDTOMapper(), LocationDTOMapper()).mapToDomain(this)

/**
 * Gets the value of a [LiveData] or waits for it to have one, with a timeout.
 *
 * Use this extension from host-side (JVM) tests. It's recommended to use it alongside
 * `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
 */
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}