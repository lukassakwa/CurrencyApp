package com.olivier.currencyapp.api.retrofit

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.olivier.currencyapp.data.Currency
import com.olivier.currencyapp.data.RatesItem
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RetrofitTest {
    private val mockWebServer = MockWebServer()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var client : OkHttpClient
    private lateinit var api : NBPService
    private lateinit var sut : NBPRepository

    @Before
    fun setup(){
        client = OkHttpClient
            .Builder()
            .addInterceptor(NetworkInterceptor())
            .build()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("https://api.nbp.pl/api/"))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(NBPService::class.java)

        sut = NBPRepository(api)
    }

    @After
    fun tearDown(){
        mockWebServer.close()
    }

    @Test
    fun getCurrencyResponse(){
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        runBlockingTest {
            val actual = sut.getCurrency("c")

            val ratesItem = listOf(RatesItem(0, "dolar amerykański", "USD", 3.826, 3.9032))

            assertThat(actual.body()!![0].rates).contains(ratesItem[0])
        }
    }
}