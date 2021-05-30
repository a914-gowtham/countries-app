package com.gowtham.template.repo

import com.gowtham.template.api.ApiHelperImpl
import javax.inject.Inject

class WeatherRepo @Inject constructor(
    private val apiHelperImpl: ApiHelperImpl,
) {


    suspend fun getWeatherByCity(city: String){
        apiHelperImpl.getClimateAndAirData(city)
    }
}