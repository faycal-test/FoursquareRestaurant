package com.appfiza.foursquare.di

import com.appfiza.foursquare.data.PlacesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PlacesRepository(get(), get(), get(), get()) }
}
