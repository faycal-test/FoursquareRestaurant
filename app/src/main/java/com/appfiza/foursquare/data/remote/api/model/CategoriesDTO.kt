package com.appfiza.foursquare.data.remote.api.model

import com.google.gson.annotations.SerializedName

data class CategoriesDTO (
	@SerializedName("id") val id : Int?,
	@SerializedName("name") val name : String?,
)