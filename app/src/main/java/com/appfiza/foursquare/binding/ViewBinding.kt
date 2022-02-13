package com.appfiza.foursquare.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.appfiza.foursquare.model.PlacePhotos
import com.appfiza.foursquare.ui.place_details.PlacePagerAdapter
import com.appfiza.foursquare.util.gone
import com.appfiza.foursquare.util.hide
import com.appfiza.foursquare.util.visible

/**
 * Created by Fayçal KADDOURI 🐈
 */
object ViewBinding {

    @JvmStatic
    @BindingAdapter("showError")
    fun showError(view: View, error: Boolean) {
        if (error) {
            view.visible()
        } else {
            view.gone()
        }
    }

    @JvmStatic
    @BindingAdapter("listPlacesPhotos")
    fun placesPhotos(view: ViewPager, listPlacePhotos: List<PlacePhotos>?) {
        listPlacePhotos?.let {
            if (it.isNotEmpty()) {
                view.visible()
                val adapter = view.adapter as PlacePagerAdapter
                adapter.images = it
                adapter.notifyDataSetChanged()
            } else {
                view.hide()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("showCard")
    fun showCard(view: View, listPlacePhotos: List<PlacePhotos>?) {
        listPlacePhotos?.let { view.visible() }
    }

}