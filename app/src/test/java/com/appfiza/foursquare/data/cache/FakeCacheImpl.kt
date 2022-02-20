package com.appfiza.foursquare.data.cache

import com.appfiza.foursquare.model.Place
import com.appfiza.foursquare.model.PlacePhotos
import com.appfiza.foursquare.utils.MockUtil.mockPlaceDTO
import com.appfiza.foursquare.utils.MockUtil.mockPlacePhotoDTO
import com.appfiza.foursquare.utils.toDomain
import com.google.android.gms.maps.model.LatLngBounds

/**
 * Created by Fayçal KADDOURI 🐈 on 13/2/2022.
 */
class FakeCacheImpl : PlacesCache {

    var returnNullForPlacesPhotos: Boolean = false

    override fun insertPlaces(places: List<Place>) {}

    override fun insertPlacePhotos(fsqID: String, photos: List<PlacePhotos>) {}

    override fun getPlaces(latLngBounds: LatLngBounds): List<Place> {
        return listOf(mockPlaceDTO("A", 23.32, 34.3).toDomain())
    }

    override fun getPlace(fsqID: String): Place? {
        return if (fsqID == "A") mockPlaceDTO("A", 12.0, 23.4).toDomain() else null
    }

    override fun getPlacesPhotos(fsqID: String): List<PlacePhotos>? {
        if (returnNullForPlacesPhotos) return null
        return if (fsqID == "A") listOf(mockPlacePhotoDTO("A").toDomain()) else null
    }
}