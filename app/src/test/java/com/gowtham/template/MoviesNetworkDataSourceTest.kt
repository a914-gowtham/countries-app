package com.gowtham.template

import com.gowtham.template.api.ApiHelperImpl
import com.gowtham.template.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.mockwebserver.MockResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

class MoviesNetworkDataSourceTest {
    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
//        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    private val sut = ApiHelperImpl(apiService = api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch movies correctly given 200 response`() {
        val response = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody("[]")
            .setResponseCode(404)
        mockWebServer.enqueue(response)

        runBlocking {
            print("dsd")
            val actual = sut.getAllCountries2()

            print("dsdsss $actual")
            response.throttleBody(1024, 1, TimeUnit.SECONDS)

            val responseCode= actual.code()
            assertThat(responseCode).isEqualTo(200)
        }
    }


}