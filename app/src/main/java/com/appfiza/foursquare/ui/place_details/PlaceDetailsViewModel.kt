package com.appfiza.foursquare.ui.place_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appfiza.foursquare.model.Place
import com.appfiza.foursquare.usecases.GetPlaceAndPhotosUseCase
import com.appfiza.foursquare.util.DataState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
class PlaceDetailsViewModel(
    private val fsqID: String,
    private val getPlaceAndPhotosUseCase: GetPlaceAndPhotosUseCase
) : ViewModel() {

    private val _errorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val errorLiveData: LiveData<Boolean> = _errorLiveData

    private val _loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private val _placeLiveData: MutableLiveData<Place> = MutableLiveData()
    val placeLiveData: LiveData<Place> = _placeLiveData

    /**
     *  When the user clicks on the "retry" button
     *  it loads again the place and place's photos
     */
    fun onClickRetryButton() {
        _errorLiveData.value = false
        loadPlaceAndPlacePhotos()
    }

    /**
     *  Load place from the cache & place's photos from cache if available
     *  or the network
     */
    fun loadPlaceAndPlacePhotos() {
        viewModelScope.launch {
            getPlaceAndPhotosUseCase(fsqID).collect {
                when (it) {
                    is DataState.Success -> {
                        _placeLiveData.value = it.data
                    }
                    is DataState.Idle -> {
                        _loadingLiveData.value = false
                    }
                    is DataState.Loading -> {
                        _loadingLiveData.value = true
                    }
                    is DataState.Error -> {
                        _errorLiveData.value = true
                    }
                    is DataState.Exception -> {
                        _errorLiveData.value = true
                    }
                }
            }
        }
    }


}