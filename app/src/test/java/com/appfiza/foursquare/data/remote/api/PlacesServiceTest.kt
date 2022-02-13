package com.appfiza.foursquare.data.remote.api

import com.appfiza.foursquare.MainCoroutinesRule
import com.nhaarman.mockitokotlin2.any
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import java.io.IOException

/**
 * Created by Fayçal KADDOURI 🐈 on 13/2/2022.
 */
@ExperimentalCoroutinesApi
class PlacesServiceTest : ApiAbstract<PlacesService>() {

    private lateinit var service: PlacesService

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @Before
    fun initService() {
        service = createService(PlacesService::class.java)
    }

    /**
     *  Testing the api call to "places/{fsq_id}/photos"
     */
    @Test
    fun fetchPlacePhotosFromNetworkTest() = runBlocking {
        enqueueResponse("PlacesPhotosResponse.json")
        val response = service.fetchPlacePhotos(anyString())
        val responseBody = requireNotNull((response as ApiResponse.Success).data)
        mockWebServer.takeRequest()

        assertThat(responseBody[0].id, `is`("5aeb7f7389ad460024c22206"))
        assertThat(responseBody[0].createdAt, `is`("2018-05-03T21:30:27.000Z"))
        assertThat(responseBody[0].prefix, `is`("https://fastly.4sqi.net/img/general/"))
        assertThat(responseBody[0].suffix, `is`( "/5435652_tVudly9wn9jCMpn9N6qT54RBpyx-rc3BGWg9o4E1gOk.jpg"))
        assertThat(responseBody[0].width, `is`( 1440))
        assertThat(responseBody[0].height, `is`( 1828))
    }

    /**
     *  Testing the api call to "places/search"
     */
    @Throws(IOException::class)
    @Test
    fun fetchPlacesListFromNetworkTest() = runBlocking {
        enqueueResponse("PlacesResponse.json")
        val response = service.fetchPlaces(swLatLng = any(), neLatLng = any())
        val responseBody = requireNotNull((response as ApiResponse.Success).data)
        mockWebServer.takeRequest()

        assertThat(responseBody.places[0].id, `is`("5e28e22128d3320008dd41e6"))
        assertThat(responseBody.places[0].name, `is`("Fishing Pear (السقالة)"))
        assertThat(responseBody.places[0].categories?.get(0)?.id, `is`(13032))
        assertThat(
            responseBody.places[0].categories?.get(0)?.name,
            `is`("Cafes, Coffee, and Tea Houses")
        )
        assertThat(responseBody.places[0].geocodes?.main?.latitude, `is`(21.6156))
        assertThat(responseBody.places[0].geocodes?.main?.longitude, `is`(39.106989))
        assertThat(responseBody.places[0].location?.formattedAddress, `is`("جدة"))
        assertThat(responseBody.places[0].location?.region, `is`("Makkah"))
    }

}