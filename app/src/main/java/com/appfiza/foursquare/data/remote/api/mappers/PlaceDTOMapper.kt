package com.appfiza.foursquare.data.remote.api.mappers

import com.appfiza.foursquare.data.remote.api.model.PlaceDTO
import com.appfiza.foursquare.model.Place
import com.appfiza.foursquare.util.DomainMapper

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
class PlaceDTOMapper(
    private val geocodesDTOMapper: GeocodesDTOMapper,
    private val locationDTOMapper: LocationDTOMapper
) : DomainMapper<PlaceDTO, Place> {

    override fun mapToDomain(entity: PlaceDTO): Place = Place(
        id = entity.id ?: throw MappingException("Foursquare ID cannot be null"),
        name = entity.name.orEmpty(),
        geocodes = geocodesDTOMapper.mapToDomain(entity.geocodes),
        location = locationDTOMapper.mapToDomain(entity.location)
    )
}