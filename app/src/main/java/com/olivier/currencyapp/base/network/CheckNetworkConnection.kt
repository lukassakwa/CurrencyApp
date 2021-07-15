package com.olivier.currencyapp.base.network

import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import com.olivier.currencyapp.repositories.ExchangeRateRepository
import com.olivier.currencyapp.utils.Variables
import javax.inject.Inject

class CheckNetworkConnection @Inject constructor(
    private val connectivityManager : ConnectivityManager,
) : ConnectivityManager.NetworkCallback(){

    fun checkConnection(){
        connectivityManager.registerDefaultNetworkCallback(this)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)

        Variables.isConnected.postValue(true)
        Log.i("TEST_INTERNET", "WORK")
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        Variables.isConnected.postValue(false)
    }
}