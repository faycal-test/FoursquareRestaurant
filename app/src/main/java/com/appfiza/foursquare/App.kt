package com.appfiza.foursquare

import android.app.Application
import com.appfiza.foursquare.data.remote.api.ApiConstants
import com.appfiza.foursquare.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Fayçal KADDOURI 🐈
 */
@ExperimentalCoroutinesApi
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(
                flowModule,
                cacheModule,
                networkModule(ApiConstants.BASE_URL),
                mappersModule,
                viewModelModule,
                repositoryModule,
            )
        }
    }

}