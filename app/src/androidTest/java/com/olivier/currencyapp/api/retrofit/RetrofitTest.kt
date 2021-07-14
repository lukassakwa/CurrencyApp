package com.olivier.currencyapp.api.retrofit

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RetrofitTest {

    private lateinit var retrofitBuilder : Retrofit
    private lateinit var ratesRetrofit: NBPService

    @Before
    fun setup(){
        retrofitBuilder = RetrofitBuilder.retrofit()
        ratesRetrofit = retrofitBuilder.create(NBPService::class.java)
    }

    @Test
    fun getRates(){

    }
}