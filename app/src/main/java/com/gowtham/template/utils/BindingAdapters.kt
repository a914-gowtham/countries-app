package com.gowtham.template.utils

import android.widget.ImageView
import androidx.core.view.setPadding
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        LogMessage.v("Image url $url")
        Utils.loadImage(view, url)
    }



}