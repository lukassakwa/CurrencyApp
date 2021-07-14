package com.olivier.currencyapp.api.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.olivier.currencyapp.data.RatesItem
import com.olivier.currencyapp.getOrAwaitValue
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RatesDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao : RatesDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.ratesDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertRatesItem() = runBlockingTest {
        val ratesItems = listOf(RatesItem(1, "USD", "USD", 1.1, 1.2))
        dao.insertAll(ratesItems)

        val allRatesItem = dao.getAll().getOrAwaitValue()

        assertEquals(allRatesItem, ratesItems)
    }

    @Test
    fun getAllRatesItem() = runBlockingTest {
        val ratesItems = listOf(RatesItem(1, "USD", "USD", 1.1, 1.2))
        dao.insertAll(ratesItems)

        val allRatesItem = dao.getAll().getOrAwaitValue()

        assertThat(allRatesItem).isNotEmpty()
    }

    @Test
    fun getFirstRateItem() = runBlockingTest {
        val ratesItems = listOf(
            RatesItem(1, "USD", "USD", 1.1, 1.2),
            RatesItem(2, "PLN", "PLN", 2.1, 3.2)
        )
        dao.insertAll(ratesItems)

        val rateItem = dao.getElement()
        val allRatesItem = dao.getAll().getOrAwaitValue()

        assertThat(allRatesItem).contains(rateItem[0])
    }

    @Test
    fun deleteAllRatesItems() = runBlockingTest {
            val ratesItems = listOf(RatesItem(1, "USD", "USD", 1.1, 1.2))
            dao.insertAll(ratesItems)
            dao.deleteAll()

            val allRatesItem = dao.getAll().getOrAwaitValue()

            assertThat(allRatesItem).isEmpty()
    }

    @Test
    fun updateAllRatesItems() = runBlockingTest {
        val insertRatesItems = listOf(RatesItem(1, "USD", "USD", 1.1, 1.2))
        dao.insertAll(insertRatesItems)
        val updateRatesItems = listOf(RatesItem(1, "PLN", "PLN", 2.1, 2.2))
        dao.updateRates(updateRatesItems)

        val allRatesItem = dao.getAll().getOrAwaitValue()

        assertEquals(allRatesItem, updateRatesItems)
    }

}