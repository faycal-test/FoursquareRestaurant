package com.appfiza.foursquare.di

import com.appfiza.foursquare.data.remote.api.mappers.GeocodesDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.LocationDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.PlaceDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.PlacePhotosDTOMapper
import org.koin.dsl.module

/**
 * Created by Fayçal KADDOURI 🐈
 */
val mappersModule = module {
    factory { GeocodesDTOMapper() }
    factory { LocationDTOMapper() }
    factory { PlaceDTOMapper(get(), get()) }
    factory { PlacePhotosDTOMapper() }
}