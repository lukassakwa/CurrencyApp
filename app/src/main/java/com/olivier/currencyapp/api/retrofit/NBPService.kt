package com.olivier.currencyapp.api.retrofit

import com.olivier.currencyapp.data.Currency
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NBPService {
    @GET("exchangerates/tables/{table}/today")
    suspend fun getCurrency(@Path("table") table : String) : Response<List<Currency>>
}