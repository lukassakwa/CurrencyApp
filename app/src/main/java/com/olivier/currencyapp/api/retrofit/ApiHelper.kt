package com.olivier.currencyapp.api.retrofit

import com.olivier.currencyapp.data.Currency
import retrofit2.Response

interface ApiHelper {
    suspend fun getCurrency(table : String) : Response<List<Currency>>
}