package com.olivier.currencyapp.data

import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("no")
    val no: String = "",
    @SerializedName("rates")
    val rates: List<RatesItem>?,
    @SerializedName("table")
    val table: String = "",
    @SerializedName("tradingDate")
    val tradingDate: String = "",
    @SerializedName("effectiveDate")
    val effectiveDate: String = ""

)