package com.appfiza.foursquare.ui.places_list

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appfiza.foursquare.util.DataState
import com.appfiza.foursquare.data.PlacesRepository
import com.appfiza.foursquare.data.Repository
import com.appfiza.foursquare.extentions.toLatLng
import com.appfiza.foursquare.model.Place
import com.appfiza.foursquare.util.Event
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
class PlacesListViewModel(
    private val lastKnownLocation: Flow<Location?>,
    private val placesRepository: Repository
) : ViewModel() {

    private val _placesLiveData: MutableLiveData<List<Place>> = MutableLiveData()
    val placesLiveData: LiveData<List<Place>> = _placesLiveData

    private val _centerMapToUserLocationLiveData: MutableLiveData<Event<LatLng>> = MutableLiveData()
    val centerMapToUserLocationLiveData: LiveData<Event<LatLng>> = _centerMapToUserLocationLiveData

    /**
     *  When the user granted location permission
     */
    fun onUserGrantedLocationPermission() {
        lastKnownLocation
            .filterNotNull()
            .onEach {
                _centerMapToUserLocationLiveData.value = Event(it.toLatLng())
            }
            .launchIn(viewModelScope)
    }

    /**
     *  When the user pans the map
     *  @param [latLngBounds] representing the south/wes & north/east points of a rectangle.
     */
    fun onMapMove(latLngBounds: LatLngBounds) {
        viewModelScope.launch {
            placesRepository.fetchPlaces(latLngBounds).collect {
                when (it) {
                    is DataState.Success -> {
                        _placesLiveData.value = it.data
                    }
                    else -> {}
                }
            }
        }
    }

}