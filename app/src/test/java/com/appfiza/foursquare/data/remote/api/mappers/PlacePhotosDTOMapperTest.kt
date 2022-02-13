package com.appfiza.foursquare.data.remote.api.mappers

import com.appfiza.foursquare.model.PlacePhotos
import com.appfiza.foursquare.utils.MockUtil.mockPlacePhotoDTO
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * Created by Fayçal KADDOURI 🐈 on 13/2/2022.
 */
class PlacePhotosDTOMapperTest {

    private val placePhotosDTOMapper = PlacePhotosDTOMapper()

    @Test
    fun `Ensure that PlacePhotosDTOMapper maps correctly the properties`() {
        // GIVEN
        val placePhotosDTO = mockPlacePhotoDTO("id")

        val placePhotos = PlacePhotos(
            id = "id",
            prefix = "https://fastly.4sqi.net/img/general/",
            suffix = "/1049719_PiLE0Meoa27AkuLvSaNwcvswnmYRa0vxLQkOrpgMlwk.jpg",
        )

        // WHEN
        val result = placePhotosDTOMapper.mapToDomain(placePhotosDTO)

        // THEN
        assertThat(result, CoreMatchers.`is`(placePhotos))
    }


}