package com.gowtham.template.api

import com.gowtham.template.models.country.Country
import com.gowtham.template.models.weather.Weather
import retrofit2.Response

interface ApiHelper {

    suspend fun getAllCountries(): Response<List<Country>>

    suspend fun getClimateAndAirData(city: String): Response<Weather>

}