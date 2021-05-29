package com.gowtham.template.di

import android.content.Context
import androidx.room.Room
import com.gowtham.template.db.CountryDatabase
import com.gowtham.template.utils.Constants.COUNTRY_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideChatUserDb(@ApplicationContext context: Context): CountryDatabase {
        return Room.databaseBuilder(
            context, CountryDatabase::class.java,
            COUNTRY_DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideCountryDao(db: CountryDatabase) = db.getCountryDao()


}