package com.appfiza.foursquare.data.remote.api.model

import com.google.gson.annotations.SerializedName


data class PlaceDTO (
	@SerializedName("fsq_id") val id : String?,
	@SerializedName("categories") val categories : List<CategoriesDTO>?,
	@SerializedName("distance") val distance : Int?,
	@SerializedName("location") val location : LocationDTO?,
	@SerializedName("name") val name : String?,
	@SerializedName("geocodes") val geocodes: GeocodesDTO?,
)