package com.olivier.currencyapp.api.retrofit


import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()

        var request = original.newBuilder()
            .header("User-Agent", "CurrencyApp")
            .header("Accept", "application/json")
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }

}