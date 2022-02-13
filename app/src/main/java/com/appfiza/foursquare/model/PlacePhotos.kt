package com.appfiza.foursquare.model

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
data class PlacePhotos(
    val id: String,
    val prefix: String,
    val suffix: String
) {

    /**
     *  Get the photo url path
     *  @param [width] represents the width of the image
     *  @param [height] represents the height of the image
     */
    fun getPhotoUrl(width: Int, height: Int): String {
        return prefix + width.toString() + "x" + height.toString() + suffix
    }
}
