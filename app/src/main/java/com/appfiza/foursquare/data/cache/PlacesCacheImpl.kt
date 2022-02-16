package com.appfiza.foursquare.data.cache

import com.appfiza.foursquare.model.Place
import com.appfiza.foursquare.model.PlacePhotos
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
class PlacesCacheImpl(
    private val hashMapLatLngPlaces: HashMap<LatLng, Place> = hashMapOf(),
    private val hashMapPlacesPhotos: HashMap<String, List<PlacePhotos>> = hashMapOf()
) : PlacesCache {


    override fun insertPlaces(places: List<Place>) {
        places.forEach { hashMapLatLngPlaces[it.toLatLng()] = it }
    }

    override fun insertPlacesPhotos(fsqID: String, photos: List<PlacePhotos>) {
        hashMapPlacesPhotos[fsqID] = photos
    }

    override fun getPlacesPhotos(fsqID: String): List<PlacePhotos>? {
        return hashMapPlacesPhotos[fsqID]
    }

    override fun getPlace(fsqID: String): Place? {
        hashMapLatLngPlaces.forEach { (_, place) ->
            if (place.id == fsqID) return place
        }
        return null
    }

    override fun getPlaces(latLngBounds: LatLngBounds): List<Place> {
        val places = mutableListOf<Place>()
        hashMapLatLngPlaces.forEach { (latLng, place) ->
            if (latLngBounds.contains(latLng)) places.add(place)
        }
        return places
    }


}