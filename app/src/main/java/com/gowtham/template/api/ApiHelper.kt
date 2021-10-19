package com.gowtham.template.api

import com.gowtham.template.models.country.Country
import com.gowtham.template.models.country.Country2
import com.gowtham.template.models.weather.Weather
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface ApiHelper {

    suspend fun getAllCountries(): Response<List<Country>>

    suspend fun getAllCountries2(): Response<List<Country2>>
    fun getAllCountries3(): Single<Response<List<Country2>>>

    suspend fun getClimateAndAirData(city: String): Response<Weather>

}