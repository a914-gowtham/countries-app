package com.gowtham.template.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.gowtham.template.models.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Query("SELECT * FROM Country")
    suspend fun getAllCountries(): Flow<List<Country>>

    @Query("SELECT * FROM Country WHERE name LIKE '%' || :query || '%'")
    suspend fun getQueriedCountries(query: String): Flow<List<Country>>
}