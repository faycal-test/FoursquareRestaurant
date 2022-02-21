package com.appfiza.foursquare.data.cache

import com.appfiza.foursquare.model.Place
import com.appfiza.foursquare.model.PlacePhotos
import com.google.android.gms.maps.model.LatLngBounds

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
class PlacesCacheImpl(
    private val hashMapPlaces: HashMap<String, Place> = hashMapOf()
) : PlacesCache {

    override fun insertPlaces(places: List<Place>) {
        places.forEach {
            if (!hashMapPlaces.containsKey(it.id)) {
                hashMapPlaces[it.id] = it
            }
        }
    }

    override fun insertPlacePhotos(fsqID: String, photos: List<PlacePhotos>) {
        hashMapPlaces[fsqID]?.copy(photos = photos)?.let { hashMapPlaces[fsqID] = it }
    }

    override fun getPlace(fsqID: String): Place? = hashMapPlaces[fsqID]

    override fun getPlaces(latLngBounds: LatLngBounds): List<Place> {
        val places = mutableListOf<Place>()
        hashMapPlaces.forEach { (_, place) ->
            if (latLngBounds.contains(place.toLatLng())) places.add(place)
        }
        return places
    }


}