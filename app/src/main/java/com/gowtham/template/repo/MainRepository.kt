package com.gowtham.template.repo

import com.gowtham.template.api.ApiHelperImpl
import com.gowtham.template.db.daos.CountryDao
import com.gowtham.template.utils.LoadState
import java.io.IOException
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelperImpl: ApiHelperImpl,
    private val countryDao: CountryDao
) {

    suspend fun getAllCountries(): LoadState {
        var list = countryDao.getAllCountries()

        return if (list.isEmpty()) {
            try {
                val response = apiHelperImpl.getAllCountries()
                list = response.body()!!
                countryDao.insertMultipleCountries(list)
                LoadState.OnSuccess(data = list)
            } catch (t: Throwable) {
                t.printStackTrace()
                when (t) {
                    is IOException -> LoadState.OnFailure("Network failure")
                    else -> LoadState.OnFailure("Conversion Error")
                }
            }
        } else
            LoadState.OnSuccess(data = list)
    }

    suspend fun getQueriedCountries(query: String): LoadState {
        val result = countryDao.getQueriedCountries(query)
        return LoadState.OnSuccess(result)
    }


}