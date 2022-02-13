package com.appfiza.foursquare.data.remote.api.mappers

import com.appfiza.foursquare.model.Place
import com.appfiza.foursquare.utils.MockUtil.mockGeocodesDTO
import com.appfiza.foursquare.utils.MockUtil.mockLocationDTO
import com.appfiza.foursquare.utils.MockUtil.mockPlaceDTO
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * Created by Fayçal KADDOURI 🐈 on 13/2/2022.
 */
class PlaceDTOMapperTest {

    private val locationDTOMapper = LocationDTOMapper()
    private val geocodesDTOMapper = GeocodesDTOMapper()
    private val placeDTOMapper = PlaceDTOMapper(geocodesDTOMapper, locationDTOMapper)

    @Test
    fun `Ensure that PlaceDTOMapper maps correctly the properties`() {
        // GIVEN
        val placeDTO = mockPlaceDTO("id", 2.3, 24.2)
        val place = Place(
            id = "id",
            name = "Some name",
            location = locationDTOMapper.mapToDomain(mockLocationDTO()),
            geocodes = geocodesDTOMapper.mapToDomain(mockGeocodesDTO(2.3, 24.2))
        )

        // WHEN
        val result = placeDTOMapper.mapToDomain(placeDTO)

        // THEN
        assertThat(result, `is`(place))
    }
}