package com.olivier.currencyapp.api.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.olivier.currencyapp.data.RatesItem

@Database(entities = arrayOf(RatesItem::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ratesDao(): RatesDao
}

