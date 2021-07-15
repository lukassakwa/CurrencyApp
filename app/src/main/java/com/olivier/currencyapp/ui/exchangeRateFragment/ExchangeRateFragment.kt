package com.olivier.currencyapp.ui.exchangeRateFragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.olivier.currencyapp.R
import com.olivier.currencyapp.data.RatesItem
import com.olivier.currencyapp.databinding.ExchangeRateFragmentBinding
import com.olivier.currencyapp.ui.adapters.recyclerview.ExchangeAdapter
import com.olivier.currencyapp.ui.adapters.spinner.ResultSpinnerAdapter
import com.olivier.currencyapp.ui.adapters.spinner.UserSpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeRateFragment : Fragment() {
    private val viewModel: ExchangeRateViewModel by viewModels()

    private lateinit var binding : ExchangeRateFragmentBinding
    private lateinit var exchangeAdapter : ExchangeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exchangeAdapter = ExchangeAdapter()
        viewModel.checkInternetConnection()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.exchange_rate_fragment,
            container,
            false
        )

        uiWidgets()
        observeRatesItems()
        observeInternetConnection()

        return binding.root
    }

    private fun uiWidgets(){
        initRecyclerView()
        initSpinner()
        initButton()
    }

    private fun observeRatesItems() {
        //observe data in database
        viewModel.ratesItems.observe(this, Observer {
            if(it.isNotEmpty()){
                updateUi()
            }else{
                disableSpinner()
            }
            updateRecyclerView(it)
        })

        //observe result
        viewModel.result.observe(this){
            binding.resultEditText.setText(it.toString())
        }
    }

    private fun observeInternetConnection() {
        viewModel.isNetworkConnected.observe(this, Observer {
            if(it){
                viewModel.updateRatesItems()
            }
        })
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(context!!, R.array.currency_array, android.R.layout.simple_spinner_dropdown_item,).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.userSpinner.adapter = adapter
            binding.resultSpinner.adapter = adapter
        }
        binding.userSpinner.setSelection(0)
        binding.resultSpinner.setSelection(1)
    }

    private fun initRecyclerView() {
        binding.currencyRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = exchangeAdapter
        }
    }

    private fun initButton() {
        binding.convertButton.setOnClickListener{ onConvert() }
    }

    private fun updateUi() {
        viewModel.setUserExchange(0)
        viewModel.setResultExchange(1)

        binding.userSpinner.onItemSelectedListener = UserSpinnerAdapter(viewModel)
        binding.userSpinner.isEnabled = true

        binding.resultSpinner.onItemSelectedListener = ResultSpinnerAdapter(viewModel)
        binding.resultSpinner.isEnabled = true
    }

    private fun disableSpinner(){
        binding.userSpinner.isEnabled = false
        binding.resultSpinner.isEnabled = false
    }

    private fun updateRecyclerView(ratesItems: List<RatesItem>) {
        exchangeAdapter.setCurrency(ratesItems)
        binding.currencyRecyclerView.adapter!!.notifyDataSetChanged()
    }

    private fun onConvert(){
        var text = binding.userInputEditText.text.toString()
        if(!TextUtils.isEmpty(text)){
            viewModel.convert(text.toDouble())
        }else{
            binding.userInputEditText.error = "Wpisz wartosc"
        }
    }
}
