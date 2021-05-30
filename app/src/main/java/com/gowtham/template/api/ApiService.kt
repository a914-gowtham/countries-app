package com.gowtham.template.api

import com.gowtham.template.models.country.Country
import com.gowtham.template.models.weather.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object{
        const val API_KEY = "5eb40805bac24b7b9a8214212212905"
    }

    @GET("all")
    suspend fun getPokemonList(): Response<List<Country>>

    @GET("current.json")
    suspend fun getClimateAndAirData(
        @Query("q")
        city: String,
        @Query("key")
        apiKey: String = API_KEY,
        @Query("aqi")
        airData: String = "yes"
    ): Response<Weather>
}