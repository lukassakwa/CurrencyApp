package com.olivier.currencyapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.olivier.currencyapp.api.retrofit.NBPRepository
import com.olivier.currencyapp.api.room.RatesDao
import com.olivier.currencyapp.base.network.CheckNetworkConnection
import com.olivier.currencyapp.data.RatesItem
import com.olivier.currencyapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExchangeRateRepository @Inject constructor(
    private val ratesDao: RatesDao,
    private val nbpRepository: NBPRepository
){
    fun saveRateItems(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = nbpRepository.getCurrency(Constants.TABLE)
            if(response.isSuccessful){
                val rates = response.body()!![0].rates!!
                if(ratesDao.getElement().isEmpty()){
                    ratesDao.insertAll(rates)
                    Log.i("UPDATE", "${ratesDao.getElement().isEmpty()}")
                }else{
                    ratesDao.deleteAll()
                    ratesDao.insertAll(rates)
                }
            } else {
                Log.i("TEST_RETROFIT", response.message())
            }
        }
    }

    fun getRatesItems(): LiveData<List<RatesItem>> {
        return ratesDao.getAll()
    }
}