package com.appfiza.foursquare.data

import androidx.annotation.WorkerThread
import com.appfiza.foursquare.data.cache.PlacesCache
import com.appfiza.foursquare.data.remote.api.PlacesService
import com.appfiza.foursquare.data.remote.api.mappers.PlaceDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.PlacePhotosDTOMapper
import com.appfiza.foursquare.extentions.neLatLng
import com.appfiza.foursquare.extentions.swLatLng
import com.appfiza.foursquare.model.Place
import com.appfiza.foursquare.util.DataState
import com.google.android.gms.maps.model.LatLngBounds
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
class PlacesRepository(
    private val placesCache: PlacesCache,
    private val placesService: PlacesService,
    private val placeDTOMapper: PlaceDTOMapper,
    private val placePhotosDTOMapper: PlacePhotosDTOMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {

    // Mutex to make writes to cached values thread-safe.
    private val placeMutex = Mutex()

    override fun getPlace(fsqID: String): Place? = placesCache.getPlace(fsqID)


    @WorkerThread
    override fun fetchPlacePhotos(fsqID: String) = flow {
        val cachedPlacePhotos = getPlace(fsqID)?.photos ?: emptyList()

        if (cachedPlacePhotos.isNotEmpty())  {
            emit(DataState.Success(cachedPlacePhotos))
        } else {
            placesService.fetchPlacePhotos(fsqID)
                .suspendOnSuccess {
                    val placePhotosList = data.map { placePhotosDTOMapper.mapToDomain(it) }
                    placesCache.insertPlacePhotos(fsqID, placePhotosList)
                    emit(DataState.Success(Unit))
                }
                .suspendOnError { emit(DataState.Error) }
                .suspendOnException { emit(DataState.Exception) }
        }

    }.flowOn(ioDispatcher)


    @WorkerThread
    override fun fetchPlaces(latLngBounds: LatLngBounds) = flow {
        placesService.fetchPlaces(
            swLatLng = latLngBounds.swLatLng(),
            neLatLng = latLngBounds.neLatLng()
        )
            .suspendOnSuccess {
                val placesFromNetwork = data.places.map { placeDTOMapper.mapToDomain(it) }

                placeMutex.withLock {
                    placesCache.insertPlaces(placesFromNetwork)
                }

                val cachedPlaces = placeMutex.withLock {
                    placesCache.getPlaces(latLngBounds)
                }

                emit(DataState.Success(cachedPlaces))
            }
            .suspendOnError { emit(DataState.Error) }
            .suspendOnException { emit(DataState.Exception) }
    }.onStart { emit(DataState.Loading) }.onCompletion { emit(DataState.Idle) }.flowOn(ioDispatcher)


}