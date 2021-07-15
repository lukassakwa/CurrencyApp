package com.olivier.currencyapp.ui.adapters.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.olivier.currencyapp.R
import com.olivier.currencyapp.data.RatesItem
import com.olivier.currencyapp.databinding.ExhcangeRecyclerViewAdapterBinding

class ExchangeAdapter : RecyclerView.Adapter<ExchangeAdapter.ViewHolder>() {

    private var ratesList : List<RatesItem>? = listOf()

    fun setCurrency(_ratesList: List<RatesItem>?){
        ratesList = _ratesList
    }

    inner class ViewHolder(itemView: ExhcangeRecyclerViewAdapterBinding) : RecyclerView.ViewHolder(itemView.root){
        val currency = itemView.currency
        val buyExchange = itemView.buyExchange
        val sellExchange = itemView.sellExchange
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ExhcangeRecyclerViewAdapterBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.exhcange_recycler_view_adapter,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.currency.text = "${ratesList!![position].code} :"
        holder.buyExchange.text = ratesList!![position].bid.toString()
        holder.sellExchange.text = ratesList!![position].ask.toString()
    }

    override fun getItemCount(): Int {
        return ratesList!!.size
    }


}