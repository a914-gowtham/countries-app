package com.gowtham.template.api

import com.gowtham.template.models.Country
import retrofit2.Response

interface ApiHelper {

    suspend fun getAllCountries(): Response<List<Country>>

}