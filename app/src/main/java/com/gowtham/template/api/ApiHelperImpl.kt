package com.gowtham.template.api

import com.gowtham.template.models.country.Country
import com.gowtham.template.models.country.Country2
import com.gowtham.template.models.weather.Weather
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService,
     @Named("WeatherApi")
     private val climateApiService: ApiService?=null) : ApiHelper {

    override suspend fun getAllCountries(): Response<List<Country>> {
        return apiService.getPokemonList()
    }

    override suspend fun getAllCountries2(): Response<List<Country2>> {
        return apiService.getPokemonList2()
    }

    override fun getAllCountries3(): Single<Response<List<Country2>>> {
        return apiService.getPokemonList3()
    }


    override suspend fun getClimateAndAirData(city: String): Response<Weather> {
        return climateApiService!!.getClimateAndAirData(city)
    }
}