package com.appfiza.foursquare.data

import app.cash.turbine.test
import com.appfiza.foursquare.util.DataState
import com.appfiza.foursquare.MainCoroutinesRule
import com.appfiza.foursquare.data.cache.FakeCacheImpl
import com.appfiza.foursquare.data.remote.api.PlacesService
import com.appfiza.foursquare.data.remote.api.mappers.GeocodesDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.LocationDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.PlaceDTOMapper
import com.appfiza.foursquare.data.remote.api.mappers.PlacePhotosDTOMapper
import com.appfiza.foursquare.data.remote.api.model.PlacesDTO
import com.appfiza.foursquare.extentions.neLatLng
import com.appfiza.foursquare.extentions.swLatLng
import com.appfiza.foursquare.utils.MockUtil.mockPlaceDTO
import com.appfiza.foursquare.utils.MockUtil.mockPlacePhotoDTO
import com.appfiza.foursquare.utils.toDomain
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.nhaarman.mockitokotlin2.*
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import retrofit2.Response
import kotlin.time.ExperimentalTime

/**
 * Created by Fayçal KADDOURI 🐈 on 13/2/2022.
 */
@ExperimentalTime
class PlacesRepositoryTest {

    private lateinit var repository: PlacesRepository

    private val service: PlacesService = mock()
    private val fakePlacesCache = spy(FakeCacheImpl())
    private val placePhotosDTOMapper = PlacePhotosDTOMapper()
    private val placeDTOMapper = PlaceDTOMapper(GeocodesDTOMapper(), LocationDTOMapper())

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        repository =
            PlacesRepository(fakePlacesCache, service, placeDTOMapper, placePhotosDTOMapper)
    }

    @Test
    fun `Ensure getPlace return a place it is available in memory`() {
        // GIVEN
        val fsqID = "A"
        val expectedResult = mockPlaceDTO("A", 12.0, 23.4).toDomain()

        // WHEN
        val result = repository.getPlace(fsqID)

        // THEN
        assertThat(result, `is`(expectedResult))
        verify(fakePlacesCache, atLeastOnce()).getPlace(fsqID)
        verifyNoMoreInteractions(fakePlacesCache)
    }

    @Test
    fun `Ensure getPlace return null when it is not available in memory`() {
        // GIVEN
        val fsqID = "B"
        val expectedResult = null

        // WHEN
        val result = repository.getPlace(fsqID)

        // THEN
        assertEquals(expectedResult, result)

        verify(fakePlacesCache, atLeastOnce()).getPlace(fsqID)
        verifyNoMoreInteractions(fakePlacesCache)
    }

    @Test
    fun `Ensure fetchPlacePhotos emit DataState Success when place photos available in memory`() =
        runBlocking {
            // GIVEN
            val fsqID = "A"
            val expectedResult = DataState.Success(listOf(mockPlacePhotoDTO("A").toDomain()))

            // WHEN
            val result = repository.fetchPlacePhotos(fsqID)

            // THEN
            result.test {
                assertThat(awaitItem(), `is`(DataState.Loading))
                val expectedItem = awaitItem()
                assertThat(expectedItem, `is`(expectedResult))
                assertThat(awaitItem(), `is`(DataState.Idle))
                awaitComplete()
            }

            verify(fakePlacesCache, atLeastOnce()).getPlacesPhotos(fsqID)
            verifyNoMoreInteractions(fakePlacesCache)
        }


    @Test
    fun `Ensure fetchPlacePhotos emit DataState Success when place photos are retrieved from network and cache is empty`() =
        runBlocking {
            // GIVEN
            val fsqID = "B"
            val expectedResultToBeCached = listOf(mockPlacePhotoDTO("A").toDomain())
            val response = listOf(mockPlacePhotoDTO("A"))
            val expectedResult = DataState.Success(listOf(mockPlacePhotoDTO("A").toDomain()))
            whenever(service.fetchPlacePhotos(anyString())).thenReturn(
                ApiResponse.of { Response.success(response) }
            )

            // WHEN
            val result = repository.fetchPlacePhotos(fsqID)

            // THEN
            result.test {
                assertThat(awaitItem(), `is`(DataState.Loading))
                val expectedItem = awaitItem()
                assertThat(expectedItem, `is`(expectedResult))
                assertThat(awaitItem(), `is`(DataState.Idle))
                awaitComplete()
            }

            verify(fakePlacesCache, atLeastOnce()).getPlacesPhotos(fsqID)
            verify(fakePlacesCache, atLeastOnce()).insertPlacePhotos(fsqID, expectedResultToBeCached)
            verifyNoMoreInteractions(fakePlacesCache)
        }

    @Test
    fun `Ensure fetchPlaces emit list of places`() = runBlocking {
        // GIVEN
        val sw = LatLng(1.1, 1.2)
        val ne = LatLng(34.0, 24.3)
        val latLngBounds = LatLngBounds(sw, ne)
        val expectedPlacesToBeStored = listOf(mockPlaceDTO("A", 23.32, 34.3).toDomain())
        val response = PlacesDTO(listOf(mockPlaceDTO("A", 23.32, 34.3)))
        val expectedResult = DataState.Success(listOf(mockPlaceDTO("A", 23.32, 34.3).toDomain()))
        whenever(service.fetchPlaces(swLatLng = latLngBounds.swLatLng(), neLatLng = latLngBounds.neLatLng())).thenReturn(
            ApiResponse.of { Response.success(response) }
        )

        // WHEN
        val result = repository.fetchPlaces(latLngBounds)

        // THEN
        result.test {
            assertThat(awaitItem(), `is`(DataState.Loading))
            val expectedItem = awaitItem()
            assertThat(expectedItem, `is`(expectedResult))
            assertThat(awaitItem(), `is`(DataState.Idle))
            awaitComplete()
        }

        verify(fakePlacesCache, atLeastOnce()).getPlaces(latLngBounds)
        verify(fakePlacesCache, atLeastOnce()).insertPlaces(expectedPlacesToBeStored)
        verifyNoMoreInteractions(fakePlacesCache)
    }


}