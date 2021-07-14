package com.olivier.currencyapp.api.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.olivier.currencyapp.data.RatesItem

@Database(entities = arrayOf(RatesItem::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ratesDao(): RatesDao
}

