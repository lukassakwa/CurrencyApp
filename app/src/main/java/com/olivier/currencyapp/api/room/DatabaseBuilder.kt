package com.olivier.currencyapp.api.room

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    fun getRoom(context : Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
}