package com.appfiza.foursquare.data.cache

import com.appfiza.foursquare.model.Place
import com.appfiza.foursquare.model.PlacePhotos
import com.google.android.gms.maps.model.LatLngBounds

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
interface PlacesCache {

    /**
     *  Insert a list of places to be stored in memory
     *  @param [places] List of [Place]
     */
    fun insertPlaces(places: List<Place>)

    /**
     *  Insert place's photos to be stored in memory
     *  @param [fsqID] represents the Foursquare ID place of a place
     *  @param [photos] List of [PlacePhotos]
     */
    fun insertPlacesPhotos(fsqID: String, photos: List<PlacePhotos>)

    /**
     *  Get places that are inside a specific area (rectangle)
     *  @param [latLngBounds] representing the south/wes & north/east points of a rectangle.
     */
    fun getPlaces(latLngBounds: LatLngBounds): List<Place>

    /**
     *  Get a place by ID
     *
     *  If the place isn't found, it returns NULL
     *  @param [fsqID] represents the Foursquare ID
     */
    fun getPlace(fsqID: String): Place?

    /**
     *  Get place's photos by ID
     *
     *  If the place's photos are not found, it returns NULL
     *  @param [fsqID] represents the Foursquare ID of a place
     */
    fun getPlacesPhotos(fsqID: String): List<PlacePhotos>?

}