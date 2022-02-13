package com.appfiza.foursquare.data.remote.api.model

import com.google.gson.annotations.SerializedName


data class MainDTO (
	@SerializedName("latitude") val latitude : Double?,
	@SerializedName("longitude") val longitude : Double?
)