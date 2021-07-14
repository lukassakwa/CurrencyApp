package com.olivier.currencyapp.di

import com.olivier.currencyapp.api.retrofit.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideBaseUrl() = "https://api.nbp.pl/api/"

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient
        .Builder()
        .addInterceptor(NetworkInterceptor())
        .build()

    @Provides
    @Singleton
    fun providerRetrofit(okHttpClient: OkHttpClient, BASE_URL : String) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideNbpService(retrofit: Retrofit): NBPService = retrofit.create(NBPService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(nbpRepository: NBPRepository): ApiHelper = nbpRepository
}