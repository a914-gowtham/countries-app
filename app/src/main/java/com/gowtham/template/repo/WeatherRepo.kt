package com.gowtham.template.repo

import com.gowtham.template.api.ApiHelperImpl
import com.gowtham.template.utils.LoadState
import java.io.IOException
import javax.inject.Inject

class WeatherRepo @Inject constructor(
    private val apiHelperImpl: ApiHelperImpl,
) {


    suspend fun getWeatherByCity(city: String): LoadState {
        return try {
            val response = apiHelperImpl.getClimateAndAirData(city)
            val weather = response.body()!!
            LoadState.OnSuccess(data = weather)
        } catch (t: Throwable) {
            t.printStackTrace()
            when (t) {
                is IOException -> LoadState.OnFailure("Network failure")
                else -> LoadState.OnFailure("Conversion Error")
            }
        }
    }

}