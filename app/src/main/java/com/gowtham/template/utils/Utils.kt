package com.gowtham.template.utils

import android.widget.ImageView
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory


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