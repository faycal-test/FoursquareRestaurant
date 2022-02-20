package com.appfiza.foursquare.usecases

import com.appfiza.foursquare.data.Repository
import com.appfiza.foursquare.util.DataState
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform

/**
 * Created by Fayçal KADDOURI 🐈 on 20/2/2022.
 */
class GetPlaceAndPhotosUseCase(private val repository: Repository) {

    operator fun invoke(fsqID: String) = repository.fetchPlacePhotos(fsqID).transform {
        when (it) {
            is DataState.Success -> {
                repository.getPlace(fsqID)?.let { place ->
                    emit(DataState.Success(place))
                }
            }
            else -> emit(DataState.Error)
        }
    }.onStart { emit(DataState.Loading) }.onCompletion { emit(DataState.Idle) }

}