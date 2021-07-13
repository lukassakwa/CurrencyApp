package com.olivier.currencyapp.api.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
        private const val API_BASE_URL = "https://api.nbp.pl/api/"

        fun retrofit() : Retrofit {

            val httpClient : OkHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(NetworkInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        }
}
