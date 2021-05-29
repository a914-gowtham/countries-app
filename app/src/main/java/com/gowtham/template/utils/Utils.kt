package com.gowtham.template.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
import android.widget.ImageView
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.ViewSizeResolver
import coil.transform.CircleCropTransformation
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gowtham.template.R
import retrofit2.converter.gson.GsonConverterFactory


object Utils {

    fun loadImage(imageView: ImageView, imgUrl: String){
        imageView.load(imgUrl) {
            crossfade(true)
            crossfade(300)
            diskCachePolicy(CachePolicy.ENABLED)
            placeholder(R.drawable.ic_launcher_background)
            error(R.drawable.ic_launcher_background)
        }
    }

    fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}