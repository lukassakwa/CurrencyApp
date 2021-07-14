package com.olivier.currencyapp.base.network

import android.net.ConnectivityManager
import android.net.Network
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.olivier.currencyapp.repositories.ExchangeRateRepository
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

class CheckNetworkConnection @Inject constructor(
    private val connectivityManager : ConnectivityManager,
    private val exchangeRateRepository: ExchangeRateRepository
    ) : ConnectivityManager.NetworkCallback(){

    fun checkConnection(){
        connectivityManager.registerDefaultNetworkCallback(this)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        //download rest api from internet and save in DataStore
        exchangeRateRepository.currencyFromRestApi()
        Log.i("TEST_INTERNET", "WORK")
    }
}
