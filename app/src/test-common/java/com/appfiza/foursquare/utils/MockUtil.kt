package com.appfiza.foursquare.utils

import com.appfiza.foursquare.data.remote.api.model.*

/**
 * Created by Fayçal KADDOURI 🐈
 */


object MockUtil {

    fun mockPlacePhotoDTO(id: String) = PlacePhotosDTO(
        id = id,
        createdAt = "2013-05-03T02:38:34.000Z",
        prefix = "https://fastly.4sqi.net/img/general/",
        suffix = "/1049719_PiLE0Meoa27AkuLvSaNwcvswnmYRa0vxLQkOrpgMlwk.jpg",
        width = 1920,
        height = 1440,
        classifications = null
    )

    private fun mockMainDTO(lat: Double, lng: Double) = MainDTO(
        latitude = lat,
        longitude = lng
    )

    fun mockGeocodesDTO(lat: Double, lng: Double) = GeocodesDTO(
        main = mockMainDTO(lat, lng)
    )

    fun mockLocationDTO() = LocationDTO(
        address = "Some Address",
        adminRegion = "Some admin region",
        country = "Some country",
        formattedAddress = "Some formatted address",
        locality = "Some locality",
        postcode = "Some postcode",
        region = "Some region"
    )

    fun mockPlaceDTO(id: String, lat: Double, lng: Double) = PlaceDTO(
        id = id,
        name = "Some name",
        categories = null,
        distance = null,
        location = mockLocationDTO(),
        geocodes = mockGeocodesDTO(lat, lng)
    )

}

