package com.gowtham.template.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gowtham.template.models.country.Country

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleCountries(users: List<Country>)

    @Query("SELECT * FROM Country")
    suspend fun getAllCountries(): List<Country>

    @Query("SELECT * FROM Country WHERE name LIKE '%' || :query || '%'")
    suspend fun getQueriedCountries(query: String): List<Country>
}