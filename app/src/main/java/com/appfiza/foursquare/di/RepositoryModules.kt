package com.appfiza.foursquare.di

import com.appfiza.foursquare.data.PlacesRepository
import com.appfiza.foursquare.data.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single<Repository> { PlacesRepository(get(), get(), get(), get()) }
}
