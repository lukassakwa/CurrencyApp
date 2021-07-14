package com.olivier.currencyapp.ui.adapters.spinner

import android.view.View
import android.widget.AdapterView
import com.olivier.currencyapp.viewmodel.ExchangeRateViewModel

class UserSpinnerAdapter(var viewModel: ExchangeRateViewModel) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.setUserExchange(position-1)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}