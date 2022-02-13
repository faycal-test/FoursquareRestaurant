package com.appfiza.foursquare.util.custering

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
class ClusterPlace(
    private val position: LatLng,
    private val fsqID: String
) : ClusterItem {

    override fun getPosition(): LatLng = position

    override fun getTitle(): String? = null

    override fun getSnippet(): String? = null

    fun getFsqID() = fsqID

}
