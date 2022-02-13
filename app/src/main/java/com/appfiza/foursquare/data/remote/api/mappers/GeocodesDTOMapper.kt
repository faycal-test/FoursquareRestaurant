package com.appfiza.foursquare.data.remote.api.mappers

import com.appfiza.foursquare.data.remote.api.model.GeocodesDTO
import com.appfiza.foursquare.model.Geocodes
import com.appfiza.foursquare.util.DomainMapper

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
class GeocodesDTOMapper : DomainMapper<GeocodesDTO?, Geocodes> {
    override fun mapToDomain(entity: GeocodesDTO?): Geocodes = Geocodes(
        latitude = entity?.main?.latitude ?: 0.0,
        longitude = entity?.main?.longitude ?: 0.0
    )
}