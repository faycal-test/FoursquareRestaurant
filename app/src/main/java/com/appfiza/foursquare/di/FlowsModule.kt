package com.appfiza.foursquare.di

import com.appfiza.foursquare.util.lastKnownLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

/**
 * Created by Fayçal KADDOURI 🐈 on 13/2/2022.
 */
@ExperimentalCoroutinesApi
val flowModule = module {
    factory { lastKnownLocation(get()) }
}