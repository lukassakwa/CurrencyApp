package com.olivier.currencyapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.olivier.currencyapp.api.retrofit.NBPRepository
import com.olivier.currencyapp.api.room.RatesDao
import com.olivier.currencyapp.data.RatesItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExchangeRateRepository(
    private val ratesDao: RatesDao
) {
    fun currencyFromRestApi(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = NBPRepository().getCurrency("C")
            if(response.isSuccessful){
                //changing mid
                var rates = response.body()!![0].rates!!
                if(ratesDao.getElement().isEmpty()){
                    ratesDao.insertAll(rates)
                }else{
                    ratesDao.updateRates(rates)
                }
            }else{
                Log.i("TEST_RETROFIT", response.message())
            }
        }
    }

    fun readFromDatabase(): LiveData<List<RatesItem>> {
        return ratesDao.getAll()
    }
}