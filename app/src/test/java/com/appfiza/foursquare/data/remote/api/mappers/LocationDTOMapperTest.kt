package com.appfiza.foursquare.data.remote.api.mappers

import com.appfiza.foursquare.model.Location
import com.appfiza.foursquare.utils.MockUtil.mockLocationDTO
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * Created by Fayçal KADDOURI 🐈 on 13/2/2022.
 */
class LocationDTOMapperTest {

    private val locationDTOMapper = LocationDTOMapper()

    @Test
    fun `Ensure that LocationDTOMapper maps correctly the properties`() {
        // GIVEN
        val locationDTO = mockLocationDTO()
        val location = Location(
            address = "Some Address",
            adminRegion = "Some admin region",
            country = "Some country",
            formattedAddress = "Some formatted address",
            locality = "Some locality",
            postcode = "Some postcode",
            region = "Some region"
        )

        // WHEN
        val result = locationDTOMapper.mapToDomain(locationDTO)

        // THEN
        assertThat(result, CoreMatchers.`is`(location))
    }


}