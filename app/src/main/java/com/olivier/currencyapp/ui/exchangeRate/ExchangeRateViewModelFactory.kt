package com.olivier.currencyapp.ui.exchangeRate

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olivier.currencyapp.base.network.CheckNetworkConnection
import com.olivier.currencyapp.repositories.ExchangeRateRepository
import java.lang.IllegalArgumentException

class ExchangeRateViewModelFactory(
    private val exchangeRateRepository : ExchangeRateRepository,
    private val checkNetworkConnection : CheckNetworkConnection
    ) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ExchangeRateViewModel::class.java)){
            return ExchangeRateViewModel(exchangeRateRepository, checkNetworkConnection) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}