package com.olivier.currencyapp.viewmodel

import androidx.lifecycle.*
import com.olivier.currencyapp.base.network.CheckNetworkConnection
import com.olivier.currencyapp.data.RatesItem
import com.olivier.currencyapp.repositories.ExchangeRateRepository

class ExchangeRateViewModel(
    private val exchangeRateRepository : ExchangeRateRepository,
    private val checkNetworkConnection : CheckNetworkConnection
) : ViewModel() {
    var ratesModel : LiveData<List<RatesItem>> = exchangeRateRepository.readFromDatabase()

    var result : MutableLiveData<Double> = MutableLiveData(0.0)

    private var buyExchange: Double = 0.0
    private var sellExchange : Double = 0.0

    fun checkInternetConnection(){
        checkNetworkConnection.checkConnection()
    }

    fun convert(userInput : Double){
        if (buyExchange != 0.0 && sellExchange != 0.0 && userInput != 0.0) {
            var localResult = (userInput * buyExchange) / sellExchange
            result.value = kotlin.math.round(localResult * 10000.0) / 10000.0
        }
    }

    fun setUserExchange(position : Int){
        buyExchange = if(position == -1)
                0.0 else ratesModel.value!![position-1].bid
    }

    fun setResultExchange(position : Int){
        sellExchange = if(position == -1)
                0.0 else ratesModel.value!![position-1].ask
    }
}
