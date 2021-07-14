package com.olivier.currencyapp.ui.exchangeRate

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.olivier.currencyapp.R
import com.olivier.currencyapp.api.room.DatabaseBuilder
import com.olivier.currencyapp.base.network.CheckNetworkConnection
import com.olivier.currencyapp.databinding.ExchangeRateFragmentBinding
import com.olivier.currencyapp.repositories.ExchangeRateRepository
import com.olivier.currencyapp.ui.adapters.recyclerview.ExchangeAdapter
import com.olivier.currencyapp.ui.adapters.spinner.ResultSpinnerAdapter
import com.olivier.currencyapp.ui.adapters.spinner.UserSpinnerAdapter
import com.olivier.currencyapp.viewmodel.ExchangeRateViewModel
import com.olivier.currencyapp.viewmodel.ExchangeRateViewModelFactory

class ExchangeRateFragment : Fragment() {
    private lateinit var viewModel: ExchangeRateViewModel
    private lateinit var binding : ExchangeRateFragmentBinding
    private lateinit var exchangeAdapter : ExchangeAdapter
    private lateinit var checkNetworkConnection : CheckNetworkConnection

    private lateinit var exchangeRateRepository: ExchangeRateRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val connectivityManager = context!!.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ratesDao = DatabaseBuilder.getRoom(context!!).ratesDao()

        exchangeRateRepository = ExchangeRateRepository(ratesDao)
        checkNetworkConnection = CheckNetworkConnection(connectivityManager, exchangeRateRepository)
        exchangeAdapter = ExchangeAdapter()

        viewModel =
            ViewModelProvider(this, ExchangeRateViewModelFactory(exchangeRateRepository, checkNetworkConnection)).get(
                ExchangeRateViewModel::class.java)
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
        observeViewModelData()

        return binding.root
    }

    private fun uiWidgets(){
        //recyclerView
        binding.currencyRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = exchangeAdapter
        }

        //spinner
        ArrayAdapter.createFromResource(context!!, R.array.currency_array, android.R.layout.simple_spinner_dropdown_item,).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.userSpinner.adapter = adapter
            binding.resultSpinner.adapter = adapter
        }

        //function on button click
        binding.convertButton.setOnClickListener{ onConvert() }
    }

    private fun observeViewModelData() {
        //observe data in database
        viewModel.ratesModel.observe(this, Observer {
            if(it.isNotEmpty()){
                exchangeAdapter.setCurrency(it)
                binding.currencyRecyclerView.adapter!!.notifyDataSetChanged()
                binding.userSpinner.onItemSelectedListener = UserSpinnerAdapter(viewModel)
                binding.resultSpinner.onItemSelectedListener = ResultSpinnerAdapter(viewModel)
            }else{
                Toast.makeText(context, "connect to Internet", Toast.LENGTH_LONG).show()
            }
        })

        //observe result
        viewModel.result.observe(this){
            binding.resultEditText.setText(it.toString())
        }
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
