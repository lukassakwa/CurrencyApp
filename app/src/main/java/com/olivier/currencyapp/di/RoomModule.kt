package com.olivier.currencyapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.olivier.currencyapp.api.room.AppDatabase
import com.olivier.currencyapp.api.room.RatesDao
import com.olivier.currencyapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) : AppDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, Constants.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideRatesDao(db : AppDatabase) : RatesDao = db.ratesDao()

}