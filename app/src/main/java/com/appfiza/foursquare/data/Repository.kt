package com.appfiza.foursquare.data

import com.appfiza.foursquare.model.Place
import com.appfiza.foursquare.model.PlacePhotos
import com.appfiza.foursquare.util.DataState
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.coroutines.flow.Flow

interface Repository {

    /**
     *  Get a place from cache
     *
     *  It can returns NULL if the place isn't stored
     *  @param [fsqID] represents a Foursquare ID for a place
     */
    fun getPlace(fsqID: String): Place?

    /**
     *  Get place's photos
     *
     *  It get result from cache if available or the network
     *  @param [fsqID] represents a Foursquare ID for a place
     */
    fun fetchPlacePhotos(fsqID: String): Flow<DataState<Any>>

    /**
     *  Get places list from within a specific area
     *
     *  It get result from cache if available or the network
     *  @param [latLngBounds] representing the south/wes & north/east points of a rectangle.
     */
    fun fetchPlaces(latLngBounds: LatLngBounds): Flow<DataState<List<Place>>>
}
