package com.appfiza.foursquare.di

import com.appfiza.foursquare.data.cache.PlacesCache
import com.appfiza.foursquare.data.cache.PlacesCacheImpl
import org.koin.dsl.module

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
val cacheModule = module {
    single<PlacesCache> { PlacesCacheImpl() }
}