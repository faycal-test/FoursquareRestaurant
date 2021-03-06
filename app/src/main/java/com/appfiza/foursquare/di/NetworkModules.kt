package com.appfiza.foursquare.di

import com.appfiza.foursquare.data.remote.api.ApiConstants
import com.appfiza.foursquare.data.remote.api.PlacesService
import com.google.gson.Gson
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = { baseUrl: String ->
    module {
        single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
        single {
            OkHttpClient.Builder()
                .readTimeout(ApiConstants.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(ApiConstants.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(ApiConstants.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(get<HttpLoggingInterceptor>())
                .build()
        }
        single {
            Retrofit.Builder()
                .client(get())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
                .build()
        }
        single { get<Retrofit>().create(PlacesService::class.java) }
    }
}