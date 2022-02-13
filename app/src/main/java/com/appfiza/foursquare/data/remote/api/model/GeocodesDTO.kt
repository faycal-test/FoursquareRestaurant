package com.appfiza.foursquare.data.remote.api.model

import com.google.gson.annotations.SerializedName


data class GeocodesDTO(@SerializedName("main") val main: MainDTO)