package com.olivier.currencyapp

import com.olivier.currencyapp.api.retrofit.NBPService
import com.olivier.currencyapp.api.retrofit.RetrofitBuilder
import org.junit.Test
import retrofit2.create

class RetrofitTest {
    @Test
    fun `it should GET with query`(){
        val api = RetrofitBuilder.retrofit().create(NBPService::class.java)
    }
}