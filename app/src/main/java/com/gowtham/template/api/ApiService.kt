package com.gowtham.template.api

import com.gowtham.template.models.Country
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("all")
    suspend fun getPokemonList(): Response<List<Country>>
}