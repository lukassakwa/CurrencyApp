package com.olivier.currencyapp.api.retrofit

class NBPRepository() {
    private val client = RetrofitBuilder.retrofit().create(NBPService::class.java)

    suspend fun getCurrency(table : String) = client.getCurrency(table)
}