package com.olivier.currencyapp.ui.exchangeRateFragment

import androidx.lifecycle.*
import com.olivier.currencyapp.base.network.CheckNetworkConnection
import com.olivier.currencyapp.data.RatesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExchangeRateViewModel @Inject constructor(
    private val exchangeRateRepository : ExchangeRateRepository,
    private val checkNetworkConnection : CheckNetworkConnection
) : ViewModel() {
    //get actual RatesItems list from database
    var ratesModel : LiveData<List<RatesItem>> = exchangeRateRepository.readFromDatabase()

    var result : MutableLiveData<Double> = MutableLiveData(0.0)

    private var buyExchange: Double = 0.0
    private var sellExchange : Double = 0.0

    //check if device is connected to internet
    fun checkInternetConnection(){
        checkNetworkConnection.checkConnection()
    }

    //currency conversion
    fun convert(userInput : Double){
        if (buyExchange != 0.0 && sellExchange != 0.0 && userInput != 0.0) {
            var localResult = (userInput * buyExchange) / sellExchange
            result.value = kotlin.math.round(localResult * 10000.0) / 10000.0
        }
    }

    fun setUserExchange(position : Int){
        buyExchange = ratesModel.value!![position].bid
    }

    fun setResultExchange(position : Int){
        sellExchange = ratesModel.value!![position].ask
    }
}
