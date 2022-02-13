package com.appfiza.foursquare.data.remote.api.model

import com.google.gson.annotations.SerializedName


data class LocationDTO(
    @SerializedName("address") val address: String?,
    @SerializedName("admin_region") val adminRegion: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("formatted_address") val formattedAddress: String?,
    @SerializedName("locality") val locality: String?,
    @SerializedName("postcode") val postcode: String?,
    @SerializedName("region") val region: String?
)