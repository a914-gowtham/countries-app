package com.gowtham.template.api

import com.gowtham.template.models.country.Country
import com.gowtham.template.models.weather.Weather
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService,
     @Named("WeatherApi")
     private val climateApiService: ApiService) : ApiHelper {

    override suspend fun getAllCountries(): Response<List<Country>> {
        return apiService.getPokemonList()
    }

    override suspend fun getClimateAndAirData(city: String): Response<Weather> {
        return climateApiService.getClimateAndAirData(city)
    }
}