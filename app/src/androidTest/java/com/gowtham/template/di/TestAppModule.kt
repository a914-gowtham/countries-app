package com.gowtham.template.di

import android.content.Context
import androidx.room.Room
import com.gowtham.template.db.CountryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context): CountryDatabase{
        return Room.inMemoryDatabaseBuilder(
             context,
            CountryDatabase::class.java
         ).allowMainThreadQueries().build()
    }
}