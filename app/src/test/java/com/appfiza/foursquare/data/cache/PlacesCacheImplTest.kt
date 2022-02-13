package com.appfiza.foursquare.data.cache

import com.appfiza.foursquare.model.Place
import com.appfiza.foursquare.model.PlacePhotos
import com.appfiza.foursquare.utils.MockUtil.mockPlaceDTO
import com.appfiza.foursquare.utils.MockUtil.mockPlacePhotoDTO
import com.appfiza.foursquare.utils.toDomain
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Fayçal KADDOURI 🐈 on 12/2/2022.
 */
class PlacesCacheImplTest {

    private lateinit var placesCacheImpl: PlacesCacheImpl
    private val hashMapLatLngPlaces = spy(hashMapOf<LatLng, Place>())
    private val hashMapPlacesPhotos = spy(hashMapOf<String, List<PlacePhotos>>())

    @Before
    fun initService() {
        placesCacheImpl = PlacesCacheImpl(hashMapLatLngPlaces, hashMapPlacesPhotos)
    }

    /**
     *  Insert place's photos and call getPlacesPhotos with a fake ID
     *  it should give us null
     */
    @Test
    fun `Insert place's photos, getPlacesPhotos with fake ID should return null`() {
        // GIVEN
        val placePhotos1 = mutableListOf<PlacePhotos>()
        val placePhotos2 = mutableListOf<PlacePhotos>()
        val placePhotoA = mockPlacePhotoDTO("A").toDomain()
        val placePhotoB = mockPlacePhotoDTO("B").toDomain()
        val placePhotoC = mockPlacePhotoDTO("C").toDomain()
        val fsqID1 = "some id 1"
        val fsqID2 = "some id 2"
        val fsqID3 = "some id 3"

        placePhotos1.add(placePhotoA)
        placePhotos1.add(placePhotoB)
        placePhotos2.add(placePhotoC)

        placesCacheImpl.insertPlacesPhotos(fsqID1, placePhotos1)
        placesCacheImpl.insertPlacesPhotos(fsqID2, placePhotos2)

        // WHEN
        val result = placesCacheImpl.getPlacesPhotos(fsqID3)

        // THEN
        assertEquals(null, result)
        verify(hashMapPlacesPhotos, atLeastOnce())[fsqID3]
        verifyNoMoreInteractions(hashMapLatLngPlaces)
    }

    /**
     *  Insert place's photos and call getPlacesPhotos with a specific ID
     *  it should give us the right photos that we inserted previously
     */
    @Test
    fun `Insert place's photos, getPlacesPhotos should return Place Photos List`() {
        // GIVEN
        val placePhotos1 = mutableListOf<PlacePhotos>()
        val placePhotos2 = mutableListOf<PlacePhotos>()
        val placePhotoA = mockPlacePhotoDTO("A").toDomain()
        val placePhotoB = mockPlacePhotoDTO("B").toDomain()
        val placePhotoC = mockPlacePhotoDTO("C").toDomain()
        val fsqID1 = "some id 1"
        val fsqID2 = "some id 2"

        placePhotos1.add(placePhotoA)
        placePhotos1.add(placePhotoB)
        placePhotos2.add(placePhotoC)

        placesCacheImpl.insertPlacesPhotos(fsqID1, placePhotos1)
        placesCacheImpl.insertPlacesPhotos(fsqID2, placePhotos2)

        // WHEN
        val result = placesCacheImpl.getPlacesPhotos(fsqID1)

        // THEN
        assertThat(result, `is`(placePhotos1))
        verify(hashMapPlacesPhotos, atLeastOnce())[fsqID1]
        verifyNoMoreInteractions(hashMapLatLngPlaces)
    }

    /**
     *  Check that inside the method "insertPlacesPhotos"
     *  hashMapPlacesPhotos[some id] = placePhotos is executed
     */
    @Test
    fun `Insert place's photos, ensure photos are inserted into the hashmap`() {
        // GIVEN
        val placePhotos = mutableListOf<PlacePhotos>()
        val placePhotoA = mockPlacePhotoDTO("A").toDomain()
        val placePhotoB = mockPlacePhotoDTO("B").toDomain()
        val placePhotoC = mockPlacePhotoDTO("C").toDomain()
        val fsqID = "some id"

        placePhotos.add(placePhotoA)
        placePhotos.add(placePhotoB)
        placePhotos.add(placePhotoC)

        // WHEN
        placesCacheImpl.insertPlacesPhotos(fsqID, placePhotos)

        // THEN
        verify(hashMapPlacesPhotos, atLeastOnce())[fsqID] = placePhotos
        verifyNoMoreInteractions(hashMapLatLngPlaces)
        verifyNoMoreInteractions(hashMapPlacesPhotos)
    }

    /**
     *  Check that inside the method "insertPlaces"
     *  hashMapLatLngPlaces[it.toLatLng()] = place is executed
     */
    @Test
    fun `Insert places, ensure places are inserted into the hashmap`() {
        // GIVEN
        val places = mutableListOf<Place>()
        val placeA = mockPlaceDTO("A", lat = 21.6156, lng = 39.106989).toDomain()
        val placeB = mockPlaceDTO("B", lat = 21.567233166965515, lng = 39.12502884864807).toDomain()
        val placeC = mockPlaceDTO("C", lat = -37.06, lng = 174.58).toDomain()

        places.add(placeA)
        places.add(placeB)
        places.add(placeC)

        // WHEN
        placesCacheImpl.insertPlaces(places)

        // THEN
        verify(hashMapLatLngPlaces, atLeastOnce())[placeA.toLatLng()] = placeA
        verify(hashMapLatLngPlaces, atLeastOnce())[placeB.toLatLng()] = placeB
        verify(hashMapLatLngPlaces, atLeastOnce())[placeC.toLatLng()] = placeC
        verifyNoMoreInteractions(hashMapLatLngPlaces)
        verifyNoMoreInteractions(hashMapPlacesPhotos)
    }

    /**
     *  Calling getPlace with a fsqID should give us the right place
     */
    @Test
    fun `Insert places, ensure getPlace with an ID should return the right place`() {
        // GIVEN
        val fsqID = "B"
        val places = mutableListOf<Place>()
        val placeA = mockPlaceDTO("A", lat = 21.6156, lng = 39.106989).toDomain()
        val placeB = mockPlaceDTO("B", lat = 21.567233166965515, lng = 39.12502884864807).toDomain()
        val placeC = mockPlaceDTO("C", lat = -37.06, lng = 174.58).toDomain()

        places.add(placeA)
        places.add(placeB)
        places.add(placeC)

        placesCacheImpl.insertPlaces(places)

        // WHEN
        val result = placesCacheImpl.getPlace(fsqID)

        // THEN
        assertThat(result, `is`(placeB))
        verifyNoMoreInteractions(hashMapPlacesPhotos)
    }

    /**
     *  Calling getPlace with a fake ID should give us null
     *  because we didn't insert that place
     */
    @Test
    fun `Insert places, ensure getPlace with an fake ID should return null`() {
        // GIVEN
        val fsqID = "D"
        val places = mutableListOf<Place>()
        val placeA = mockPlaceDTO("A", lat = 21.6156, lng = 39.106989).toDomain()
        val placeB = mockPlaceDTO("B", lat = 21.567233166965515, lng = 39.12502884864807).toDomain()
        val placeC = mockPlaceDTO("C", lat = -37.06, lng = 174.58).toDomain()

        places.add(placeA)
        places.add(placeB)
        places.add(placeC)

        placesCacheImpl.insertPlaces(places)

        // WHEN
        val result = placesCacheImpl.getPlace(fsqID)

        // THEN
        assertEquals(null, result)
        verifyNoMoreInteractions(hashMapPlacesPhotos)
    }

    /**
     *  We have three locations A, B, C
     *  A, B are inside the bound
     *  it should return only A, B
     */
    @Test
    fun `Insert three locations, getPlaces should return two locations inside the bound`() {
        // GIVEN
        val ne = LatLng(67.95909642639164, 56.849560886621475)
        val sw = LatLng(-39.91899110233648, -15.471841916441917)
        val latLngBounds = LatLngBounds(sw, ne)
        val places = mutableListOf<Place>()
        val placeA = mockPlaceDTO("A", lat = 21.6156, lng = 39.106989).toDomain()
        val placeB = mockPlaceDTO("B", lat = 21.567233166965515, lng = 39.12502884864807).toDomain()
        val placeC = mockPlaceDTO("C", lat = -37.06, lng = 174.58).toDomain()

        val expectedList = listOf(placeA, placeB)

        places.add(placeA)
        places.add(placeB)
        places.add(placeC)

        placesCacheImpl.insertPlaces(places)

        // WHEN
        val result = placesCacheImpl.getPlaces(latLngBounds)

        // THEN
        assertThat(result, `is`(expectedList))
    }

    /**
     *  We have three locations A, B, C
     *  Neither of the locations is inside the bound
     *  it should return an empty list
     */
    @Test
    fun `Insert three locations, getPlaces should return empty list`() {
        // GIVEN
        val ne = LatLng(34.36, -175.81)
        val sw = LatLng(-47.35, 166.28)
        val latLngBounds = LatLngBounds(sw, ne)
        val places = mutableListOf<Place>()
        val placeA = mockPlaceDTO("A", lat = 21.6156, lng = 39.106989).toDomain()
        val placeB = mockPlaceDTO("B", lat = 21.567233166965515, lng = 39.12502884864807).toDomain()
        val placeC = mockPlaceDTO("C", lat = 21.615217900071006, lng = 39.187732860445976).toDomain()

        val expectedList = listOf<Place>()

        places.add(placeA)
        places.add(placeB)
        places.add(placeC)

        placesCacheImpl.insertPlaces(places)

        // WHEN
        val result = placesCacheImpl.getPlaces(latLngBounds)

        // THEN
        assertThat(result, `is`(expectedList))
    }

}