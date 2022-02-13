package com.appfiza.foursquare.di

import com.appfiza.foursquare.ui.place_details.PlaceDetailsViewModel
import com.appfiza.foursquare.ui.places_list.PlacesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PlacesListViewModel(get(), get()) }
    viewModel { params -> PlaceDetailsViewModel(params.get(), get()) }
}
