package com.appfiza.foursquare.ui.places_list

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appfiza.foursquare.MainCoroutinesRule
import com.appfiza.foursquare.data.PlacesRepository
import com.appfiza.foursquare.data.cache.FakeCacheImpl
import com.appfiza.foursquare.data.remote.api.PlacesService
import com.appfiza.foursquare.data.remote.api.mappers.GeocodesDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.LocationDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.PlaceDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.PlacePhotosDTOMapper
import com.appfiza.foursquare.data.remote.api.model.PlacesDTO
import com.appfiza.foursquare.extentions.neLatLng
import com.appfiza.foursquare.extentions.swLatLng
import com.appfiza.foursquare.utils.MockUtil
import com.appfiza.foursquare.utils.getOrAwaitValue
import com.appfiza.foursquare.utils.toDomain
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

/**
 * Created by Fayçal KADDOURI 🐈 on 13/2/2022.
 */
@ExperimentalCoroutinesApi
class PlacesListViewModelTest {

    private val service: PlacesService = mock()
    private lateinit var repository: PlacesRepository
    private lateinit var viewModel: PlacesListViewModel
    private val fakePlacesCache = spy(FakeCacheImpl())
    private val placePhotosDTOMapper = PlacePhotosDTOMapper()
    private val placeDTOMapper = PlaceDTOMapper(GeocodesDTOMapper(), LocationDTOMapper())

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        repository =
            PlacesRepository(fakePlacesCache, service, placeDTOMapper, placePhotosDTOMapper)
        val location = Location("Some crappy provider")
        location.latitude = 0.0
        location.longitude = 0.0
        val lastKnowLocation = flow { emit(location) }
        viewModel = PlacesListViewModel(lastKnowLocation, repository)
    }

    @Test
    fun `Ensure when we call onUserGrantedLocationPermission it sends event to center the map`() {
        // GIVEN
        val expectedResult = LatLng(0.0, 0.0)

        // WHEN
        viewModel.onUserGrantedLocationPermission()

        // THEN
        val result = viewModel.centerMapToUserLocationLiveData.getOrAwaitValue(5).peekContent()

        assertThat(result, `is`(expectedResult))
    }

    @Test
    fun `Ensure when we call onMapMove it loads places`() = runBlocking {
        // GIVEN
        val expectedResult = listOf(MockUtil.mockPlaceDTO("A", 23.32, 34.3).toDomain())
        val sw = LatLng(1.1, 1.2)
        val ne = LatLng(34.0, 24.3)
        val latLngBounds = LatLngBounds(sw, ne)
        val response = PlacesDTO(listOf(MockUtil.mockPlaceDTO("A", 23.32, 34.3)))
        whenever(
            service.fetchPlaces(
                swLatLng = latLngBounds.swLatLng(),
                neLatLng = latLngBounds.neLatLng()
            )
        ).thenReturn(
            ApiResponse.of { Response.success(response) }
        )

        // WHEN
        viewModel.onMapMove(latLngBounds)

        // THEN
        val result = viewModel.placesLiveData.getOrAwaitValue()

        assertThat(result, `is`(expectedResult))
    }


}