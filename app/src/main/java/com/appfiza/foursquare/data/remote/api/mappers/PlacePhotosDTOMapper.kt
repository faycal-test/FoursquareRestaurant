package com.appfiza.foursquare.data.remote.api.mappers

import com.appfiza.foursquare.data.remote.api.model.PlacePhotosDTO
import com.appfiza.foursquare.model.PlacePhotos
import com.appfiza.foursquare.util.DomainMapper

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
class PlacePhotosDTOMapper : DomainMapper<PlacePhotosDTO, PlacePhotos> {

    override fun mapToDomain(entity: PlacePhotosDTO): PlacePhotos = PlacePhotos(
        id = entity.id,
        prefix = entity.prefix.orEmpty(),
        suffix = entity.suffix.orEmpty()
    )
}