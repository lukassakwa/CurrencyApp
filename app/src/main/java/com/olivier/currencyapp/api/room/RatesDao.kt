package com.olivier.currencyapp.api.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.olivier.currencyapp.data.RatesItem

@Dao
interface RatesDao {
    @Query("SELECT * FROM rates_items")
    fun getAll(): LiveData<List<RatesItem>>

    @Query("SELECT * FROM rates_items ORDER BY id LIMIT 1")
    fun getElement() : List<RatesItem>

    @Update
    suspend fun updateRates(ratesItems: List<RatesItem>)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(rate: List<RatesItem>)

    @Query("DELETE FROM rates_items")
    fun deleteAll()

}