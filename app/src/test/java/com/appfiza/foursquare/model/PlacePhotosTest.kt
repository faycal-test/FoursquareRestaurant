package com.appfiza.foursquare.model

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * Created by Fayçal KADDOURI 🐈 on 13/2/2022.
 */
class PlacePhotosTest {

    private lateinit var placePhotos: PlacePhotos

    @Test
    fun `Ensure getPhotoUrl return the right url path`() {
        // GIVEN
        val expectedUrl =
            "https://fastly.4sqi.net/img/general/200x200/1049719_PiLE0Meoa27AkuLvSaNwcvswnmYRa0vxLQkOrpgMlwk.jpg"
        placePhotos = PlacePhotos(
            "id",
            prefix = "https://fastly.4sqi.net/img/general/",
            suffix = "/1049719_PiLE0Meoa27AkuLvSaNwcvswnmYRa0vxLQkOrpgMlwk.jpg"
        )

        // WHEN
        val result = placePhotos.getPhotoUrl(width = 200, height = 200)

        // THEN
        assertThat(result, `is`(expectedUrl))
    }


}