package com.olivier.currencyapp.ui.exchangeRateFragment

import androidx.lifecycle.*
import com.olivier.currencyapp.base.network.CheckNetworkConnection
import com.olivier.currencyapp.data.RatesItem
import com.olivier.currencyapp.repositories.ExchangeRateRepository
import com.olivier.currencyapp.utils.Variables
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExchangeRateViewModel @Inject constructor(
    private val exchangeRateRepository : ExchangeRateRepository,
    private val checkNetworkConnection : CheckNetworkConnection
) : ViewModel() {
    var ratesItems : LiveData<List<RatesItem>> = exchangeRateRepository.getRatesItems()
    var isNetworkConnected : MutableLiveData<Boolean> = Variables.isConnected

    var result : MutableLiveData<Double> = MutableLiveData(0.0)
    private var buyExchange: Double = 0.0
    private var sellExchange : Double = 0.0

    fun setUserExchange(position : Int){
        buyExchange = ratesItems.value!![position].bid
    }

    fun setResultExchange(position : Int){
        sellExchange = ratesItems.value!![position].ask
    }

    fun checkInternetConnection(){
        checkNetworkConnection.checkConnection()
    }

    fun updateRatesItems(){
        exchangeRateRepository.saveRateItems()
    }

    fun convert(userInput : Double){
        if (buyExchange != 0.0 && sellExchange != 0.0 && userInput != 0.0) {
            var localResult = (userInput * buyExchange) / sellExchange
            result.value = kotlin.math.round(localResult * 10000.0) / 10000.0
        }
    }
}
