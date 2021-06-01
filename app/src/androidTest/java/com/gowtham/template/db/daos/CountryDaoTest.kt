package com.gowtham.template.db.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.gowtham.template.db.CountryDatabase
import com.gowtham.template.models.country.Country
import com.gowtham.template.models.country.Translations
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class CountryDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule= InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: CountryDatabase

    private lateinit var countryDao: CountryDao

    @Before
    fun setUp() {
        hiltRule.inject()
        countryDao = database.getCountryDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert_Multiple_Countries() {
        runBlockingTest {
            val country1= Country(id = 1,name = "India",alpha2Code = "alpha2-code",
            alpha3Code = "alpha3-code",altSpellings = listOf(),area = 0.2,borders = listOf(),
            callingCodes = listOf(), currencies = listOf(), demonym = "Indian",flag = "",
            gini = 2.0,languages = listOf(),latlng = listOf(),nativeName = "India",population = 1232,
            region = "Asia",regionalBlocs = listOf(),subregion = "South Asia",timezones = listOf(),
            topLevelDomain = listOf(),cioc = "",capital = "",numericCode = "",translations = Translations(
                    br = null, de = null, fa = null, es = null,fr = null, hr = null,it=null,ja = null, nl = null,
                    pt = null
                ))
            val country2=country1.copy(id = 2,name = "Australia")
            val country3=country1.copy(id = 3,name = "US")
            countryDao.insertMultipleCountries(listOf(country1,country2,country3))
            val messages=countryDao.getAllCountries()
            assertThat(messages).containsAtLeast(country1,country2,country3)
        }
    }

    @Test
    fun get_Queried_Countries() {
        runBlockingTest {
            val country1= Country(id = 1,name = "India",alpha2Code = "alpha2-code",
                alpha3Code = "alpha3-code",altSpellings = listOf(),area = 0.2,borders = listOf(),
                callingCodes = listOf(), currencies = listOf(), demonym = "Indian",flag = "",
                gini = 2.0,languages = listOf(),latlng = listOf(),nativeName = "India",population = 1232,
                region = "Asia",regionalBlocs = listOf(),subregion = "South Asia",timezones = listOf(),
                topLevelDomain = listOf(),cioc = "",capital = "",numericCode = "",translations = Translations(
                    br = null, de = null, fa = null, es = null,fr = null, hr = null,it=null,ja = null, nl = null,
                    pt = null
                )
            )
            val country2=country1.copy(id = 2,name = "Australia")
            val country3=country1.copy(id = 3,name = "US")
            countryDao.insertMultipleCountries(listOf(country1,country2,country3))
            val msg=countryDao.getQueriedCountries("Aus")
            assertThat(msg).containsExactly(country2)
        }
    }
}