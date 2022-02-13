package com.appfiza.foursquare.ui.place_details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.appfiza.foursquare.R
import com.appfiza.foursquare.model.PlacePhotos
import com.bumptech.glide.Glide


/**
 * Created by Fayçal KADDOURI 🐈 on 12/2/2022.
 */
class PlacePagerAdapter(
    private val context: Context,
    var images: List<PlacePhotos>
) : PagerAdapter() {

    private companion object {
        const val DEFAULT_IMAGE_WIDTH = 200
        const val DEFAULT_IMAGE_HEIGHT = 200
    }

    override fun getCount() = images.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = (view === `object`)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val itemView: View = inflater.inflate(R.layout.item, container, false)
        val imageView: ImageView = itemView.findViewById(R.id.imageViewPlace) as ImageView

        val imageUrl =
            images[position].getPhotoUrl(width = DEFAULT_IMAGE_WIDTH, height = DEFAULT_IMAGE_HEIGHT)
        Glide.with(context).load(imageUrl).centerCrop().into(imageView)

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

}