package com.olivier.currencyapp.ui.exchangeRateFragment

import android.content.Context
import android.net.ConnectivityManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.olivier.currencyapp.api.retrofit.NBPRepository
import com.olivier.currencyapp.api.retrofit.NBPService
import com.olivier.currencyapp.api.retrofit.NetworkInterceptor
import com.olivier.currencyapp.api.room.AppDatabase
import com.olivier.currencyapp.api.room.RatesDao
import com.olivier.currencyapp.base.network.CheckNetworkConnection
import com.olivier.currencyapp.getOrAwaitValue
import com.olivier.currencyapp.repositories.ExchangeRateRepository
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExchangeRateViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var viewModel: ExchangeRateViewModel
    private lateinit var database: AppDatabase
    private lateinit var dao : RatesDao
    private lateinit var client : OkHttpClient
    private lateinit var api : NBPService
    private lateinit var sut : NBPRepository

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.ratesDao()

        connectivityManager = ApplicationProvider.getApplicationContext<Context>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        client = OkHttpClient
            .Builder()
            .addInterceptor(NetworkInterceptor())
            .build()

        api = Retrofit.Builder()
            .baseUrl(("https://api.nbp.pl/api/"))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(NBPService::class.java)

        sut = NBPRepository(api)

        viewModel = ExchangeRateViewModel(ExchangeRateRepository(dao, sut), CheckNetworkConnection(connectivityManager))
    }

    @Test
    fun saveRatesItemFromRestApi(){
        viewModel.updateRatesItems()

        val result = dao.getAll().getOrAwaitValue()

        assertThat(result).isNotEmpty()
    }

    @After
    fun tearDown(){
        database.close()
    }
}