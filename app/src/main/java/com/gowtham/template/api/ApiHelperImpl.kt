package com.gowtham.template.api

import com.gowtham.template.models.Country
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getAllCountries(): Response<List<Country>> {
        return apiService.getPokemonList()
    }
}