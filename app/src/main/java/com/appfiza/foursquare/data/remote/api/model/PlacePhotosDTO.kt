package com.appfiza.foursquare.data.remote.api.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
data class PlacePhotosDTO(
    @SerializedName("id") val id: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("prefix") val prefix: String?,
    @SerializedName("suffix") val suffix: String?,
    @SerializedName("width") val width: Int?,
    @SerializedName("height") val height: Int?,
    @SerializedName("classifications") val classifications: List<String>?
)