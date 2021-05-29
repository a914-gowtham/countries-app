package com.gowtham.template.utils

import android.widget.ImageView
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.gowtham.template.R

object Utils {

    fun loadImage(imageView: ImageView, imgUrl: String){
        imageView.load(imgUrl) {
            crossfade(true)
            crossfade(300)
            diskCachePolicy(CachePolicy.ENABLED)
           /* placeholder(R.drawable.ic_other_user)
            error(R.drawable.ic_other_user)*/
            transformations(CircleCropTransformation())
        }
    }
}