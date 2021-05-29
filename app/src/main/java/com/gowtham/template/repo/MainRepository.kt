package com.gowtham.template.repo

import com.gowtham.template.api.ApiHelperImpl
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelperImpl: ApiHelperImpl){

    suspend fun getAllCountries() = apiHelperImpl.getAllCountries()


}