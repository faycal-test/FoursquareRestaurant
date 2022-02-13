package com.appfiza.foursquare.data.remote.api.mappers

import com.appfiza.foursquare.model.Geocodes
import com.appfiza.foursquare.utils.MockUtil.mockGeocodesDTO
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * Created by Fayçal KADDOURI 🐈 on 13/2/2022.
 */
class GeocodesDTOMapperTest {

    private val geocodesDTOMapper = GeocodesDTOMapper()

    @Test
    fun `Ensure that GeocodesDTOMapper maps correctly the properties`() {
        // GIVEN
        val geocodesDTO = mockGeocodesDTO(2.3, 1.2)
        val geocodes = Geocodes(2.3, 1.2)

        // WHEN
        val result = geocodesDTOMapper.mapToDomain(geocodesDTO)

        // THEN
        assertThat(result, `is`(geocodes))
    }


}