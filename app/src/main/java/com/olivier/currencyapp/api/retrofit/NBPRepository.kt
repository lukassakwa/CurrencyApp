package com.olivier.currencyapp.api.retrofit

import com.olivier.currencyapp.data.Currency
import retrofit2.Response
import javax.inject.Inject

class NBPRepository @Inject constructor(private val nbpService: NBPService){

    suspend fun getCurrency(table : String) : Response<List<Currency>> = nbpService.getCurrency(table)
}