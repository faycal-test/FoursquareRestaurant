package com.appfiza.foursquare.data.remote.api.model

import com.google.gson.annotations.SerializedName


data class PlacesDTO(@SerializedName("results") val places: List<PlaceDTO>)