package com.olivier.currencyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.olivier.currencyapp.ui.exchangeRate.ExchangeRateFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.first_fragment, ExchangeRateFragment())
                .commitAllowingStateLoss()
        }
    }
}
