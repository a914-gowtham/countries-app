package com.gowtham.template.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gowtham.template.db.daos.CountryDao
import com.gowtham.template.models.country.Country

@Database(
    entities = [Country::class],
    version = 1, exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun getCountryDao(): CountryDao

}