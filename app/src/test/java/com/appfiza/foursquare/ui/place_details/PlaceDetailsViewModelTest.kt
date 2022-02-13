package com.appfiza.foursquare.ui.place_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appfiza.foursquare.MainCoroutinesRule
import com.appfiza.foursquare.data.PlacesRepository
import com.appfiza.foursquare.data.cache.FakeCacheImpl
import com.appfiza.foursquare.data.remote.api.PlacesService
import com.appfiza.foursquare.data.remote.api.mappers.GeocodesDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.LocationDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.PlaceDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.PlacePhotosDTOMapper
import com.appfiza.foursquare.utils.MockUtil.mockPlaceDTO
import com.appfiza.foursquare.utils.MockUtil.mockPlacePhotoDTO
import com.appfiza.foursquare.utils.getOrAwaitValue
import com.appfiza.foursquare.utils.toDomain
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
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
class PlaceDetailsViewModelTest {

    private val fsqID = "A"
    private val service: PlacesService = mock()
    private lateinit var repository: PlacesRepository
    private lateinit var viewModel: PlaceDetailsViewModel
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
        viewModel = PlaceDetailsViewModel(fsqID, repository)
        fakePlacesCache.returnNullForPlacesPhotos = false

    }

    @Test
    fun `Ensure when we call loadPlaceAndPlacePhotos it loads place photos and place from cache`() =
        runBlocking {
            // GIVEN
            val expectedPlaceResult = mockPlaceDTO("A", 12.0, 23.4).toDomain()
            val expectedPlacePhotosResult = listOf(mockPlacePhotoDTO("A").toDomain())

            // WHEN
            viewModel.loadPlaceAndPlacePhotos()

            // THEN
            val placeResult = viewModel.placeLiveData.getOrAwaitValue()
            val placePhotosResult = viewModel.placePhotosLiveData.getOrAwaitValue()

            assertThat(placeResult, `is`(expectedPlaceResult))
            assertThat(placePhotosResult, `is`(expectedPlacePhotosResult))
        }

    @Test
    fun `Ensure when we call onClickRetryButton it loads place photos and place from cache`() =
        runBlocking {
            // GIVEN
            val expectedPlaceResult = mockPlaceDTO("A", 12.0, 23.4).toDomain()
            val expectedPlacePhotosResult = listOf(mockPlacePhotoDTO("A").toDomain())

            // WHEN
            viewModel.onClickRetryButton()

            // THEN
            val placeResult = viewModel.placeLiveData.getOrAwaitValue()
            val placePhotosResult = viewModel.placePhotosLiveData.getOrAwaitValue()
            val errorResult = viewModel.errorLiveData.getOrAwaitValue()

            assertThat(placeResult, `is`(expectedPlaceResult))
            assertThat(placePhotosResult, `is`(expectedPlacePhotosResult))
            assertThat(errorResult, `is`(false))
        }

    @Test
    fun `Call loadPlaceAndPlacePhotos but an exception happens should error live data return false`() =
        runBlocking {
            // GIVEN
            val exception = NullPointerException()
            whenever(service.fetchPlacePhotos(fsqID)).thenReturn(ApiResponse.error(exception))

            // WHEN
            fakePlacesCache.returnNullForPlacesPhotos = true
            viewModel.loadPlaceAndPlacePhotos()

            // THEN
            val result = viewModel.errorLiveData.getOrAwaitValue()

            assertThat(result, `is`(true))
        }


    @Test
    fun `Call loadPlaceAndPlacePhotos but an error happens should error live data return false`() =
        runBlocking {
            // GIVEN
            whenever(service.fetchPlacePhotos(fsqID)).thenReturn(ApiResponse.of {
                Response.error(
                    401,
                    "{\"message\": \"Lime is trying to hack Dott, help!\"}\n".toResponseBody()
                )
            })

            // WHEN
            fakePlacesCache.returnNullForPlacesPhotos = true
            viewModel.loadPlaceAndPlacePhotos()

            // THEN
            val result = viewModel.errorLiveData.getOrAwaitValue()

            assertThat(result, `is`(true))
        }


}