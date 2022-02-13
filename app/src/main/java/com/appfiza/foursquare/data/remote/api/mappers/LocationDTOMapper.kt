package com.appfiza.foursquare.data.remote.api.mappers

import com.appfiza.foursquare.data.remote.api.model.LocationDTO
import com.appfiza.foursquare.model.Location
import com.appfiza.foursquare.util.DomainMapper

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
class LocationDTOMapper : DomainMapper<LocationDTO?, Location> {

    override fun mapToDomain(entity: LocationDTO?): Location = Location(
        adminRegion = entity?.adminRegion.orEmpty(),
        address = entity?.address.orEmpty(),
        country = entity?.country.orEmpty(),
        formattedAddress = entity?.formattedAddress.orEmpty(),
        locality = entity?.locality.orEmpty(),
        postcode = entity?.postcode.orEmpty(),
        region = entity?.region.orEmpty()
    )

}