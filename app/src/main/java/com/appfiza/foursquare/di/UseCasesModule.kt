package com.appfiza.foursquare.di

import com.appfiza.foursquare.data.PlacesRepository
import com.appfiza.foursquare.usecases.GetPlaceAndPhotosUseCase
import org.koin.dsl.module

/**
 * Created by Fayçal KADDOURI 🐈
 */
val useCaseModule = module {
    factory { GetPlaceAndPhotosUseCase(get<PlacesRepository>()) }
}
