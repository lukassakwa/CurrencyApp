package com.olivier.currencyapp.ui.adapters.spinner

import android.view.View
import android.widget.AdapterView
import com.olivier.currencyapp.ui.exchangeRateFragment.ExchangeRateViewModel

class UserSpinnerAdapter(var viewModel: ExchangeRateViewModel) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.setUserExchange(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}