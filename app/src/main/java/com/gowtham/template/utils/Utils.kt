package com.gowtham.template.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.gowtham.template.R
import com.gowtham.template.utils.Constants.REQ_GPS


object Utils {

    fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    fun ImageView.loadSvg(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(300)
            .data(url)
            .diskCachePolicy(CachePolicy.ENABLED)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

    fun ImageView.loadSvgWithPlaceholder(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadSvgWithPlaceholder.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(300)
            .data(url)
            .placeholder(R.drawable.placeholder_img)
            .error(R.drawable.placeholder_img)
            .diskCachePolicy(CachePolicy.ENABLED)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

    fun ImageView.loadImage(imageUrl: String) {
        this.load(imageUrl) {
            crossfade(true)
            crossfade(300)
            diskCachePolicy(CachePolicy.ENABLED)
            /*  placeholder(R.drawable.ic_other_user)
              error(R.drawable.ic_other_user)*/
        }
    }

    fun clearNull(str: String?): String {
        return if (str.isNullOrBlank()) "Data unavailable" else str
    }

    @SuppressLint("MissingPermission")
    fun checkLocationPermission(
        context: Activity,
        fusedLocationClient: FusedLocationProviderClient,
        locationCallback: LocationCallback,
        mLocationRequest: LocationRequest,
        listenerAdded: () -> Unit
    ) {
        if (isLocationEnabled(context)) {
            LogMessage.v("location init")
            fusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, null)
            listenerAdded.invoke()
        } else {
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
            builder.setAlwaysShow(true)
            val task: Task<LocationSettingsResponse> =
                LocationServices.getSettingsClient(context).checkLocationSettings(builder.build())

            task.addOnCompleteListener {
                try {
                    val response = task.getResult(
                        ApiException::class.java
                    )
                } catch (e: ApiException) {
                    when (e.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            try {
                                // Cast to a resolvable exception.
                                val resolvable = e as ResolvableApiException
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                    context,
                                    REQ_GPS
                                )
                            } catch (e: SendIntentException) {
                                // Ignore the error.
                            } catch (e: ClassCastException) {
                                // Ignore, should be an impossible error.
                            }
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {

                        }
                    }
                }
            }
        }
    }

    private fun isLocationEnabled(context: Context): Boolean {
        var gpsEnabled = false
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }
        return gpsEnabled
    }

}