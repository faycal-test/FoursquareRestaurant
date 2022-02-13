package com.appfiza.foursquare.util

/**
 * Created by Fayçal KADDOURI 🐈
 */

/**
 *  Sealed class used to communicate with the repository
 */
sealed class DataState<out T> {
    object Idle : DataState<Nothing>()
    object Loading : DataState<Nothing>()
    object Error : DataState<Nothing>()
    object Exception : DataState<Nothing>()
    data class Success<out T>(val data: T) : DataState<T>()
}