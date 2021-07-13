package com.olivier.currencyapp.ui.exchangeRate

import androidx.lifecycle.*
import com.olivier.currencyapp.api.room.RatesDao
import com.olivier.currencyapp.base.network.CheckNetworkConnection
import com.olivier.currencyapp.data.RatesItem
import com.olivier.currencyapp.repositories.ExchangeRateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.round

class ExchangeRateViewModel(
    private val exchangeRateRepository : ExchangeRateRepository,
    private val checkNetworkConnection : CheckNetworkConnection
) : ViewModel() {
    var ratesModel : LiveData<List<RatesItem>> = exchangeRateRepository.readFromDatabase()

    var result : MutableLiveData<Double> = MutableLiveData(0.0)

    private var userInput: Double = 0.0
    private var buyExchange: Double = 0.0
    private var sellExchange : Double = 0.0

    fun checkInternetConnection(){
        checkNetworkConnection.checkConnection()
    }

    //convert
    fun convert(text : String){
        if(text.isNotEmpty()) {
            userInput = text.toDouble()
            if (buyExchange != 0.0 && sellExchange != 0.0 && userInput != 0.0) {
                var localResult = (userInput * buyExchange) / sellExchange
                result.value = kotlin.math.round(localResult * 10000.0) / 10000.0
            }
        }
    }

    fun setUserExchange(position : Int){
        buyExchange = ratesModel.value!![position-1].bid
    }

    fun setResultExchange(position : Int){
        sellExchange = ratesModel.value!![position-1].ask
    }
}
