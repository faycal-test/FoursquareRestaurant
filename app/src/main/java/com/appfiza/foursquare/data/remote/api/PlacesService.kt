package com.appfiza.foursquare.data.remote.api

import com.appfiza.foursquare.data.remote.api.model.PlacePhotosDTO
import com.appfiza.foursquare.data.remote.api.model.PlacesDTO
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Fayçal KADDOURI 🐈 on 11/2/2022.
 */
interface PlacesService {

    /**
     *  Search for places from network in the Foursquare Places database @see [Place Search](https://developer.foursquare.com/reference/place-search)
     *  @param [limit] the number of results to return
     *  @param [category] place's category ID, ex: museum or restaurant etc
     *  @param [swLatLng] the latitude/longitude representing the south/west points of a rectangle
     *  @param [neLatLng] the latitude/longitude representing the north/east points of a rectangle
     */
    @Headers("Authorization: ${ApiConstants.API_KEY}")
    @GET("places/search")
    suspend fun fetchPlaces(
        @Query("limit") limit: Int = ApiConstants.LIMIT_RESULTS,
        @Query("categories") category: String = ApiConstants.RESTAURANT_ID,
        @Query("sw") swLatLng: String,
        @Query("ne") neLatLng: String
    ): ApiResponse<PlacesDTO>

    /**
     *  Retrieve photos from network for a Foursquare Place using the fsq_id  @see [Get Place Photos](https://developer.foursquare.com/reference/place-photos)
     *  @param [id] represents a Foursquare ID for a place
     */
    @Headers("Authorization: ${ApiConstants.API_KEY}")
    @GET("places/{fsq_id}/photos")
    suspend fun fetchPlacePhotos(
        @Path("fsq_id") id: String,
    ): ApiResponse<List<PlacePhotosDTO>>
}