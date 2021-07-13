package com.olivier.currencyapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "rates_items")
data class RatesItem(
    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    val currency: String = "",

    @ColumnInfo(name = "code")
    @SerializedName("code")
    val code: String = "",

    @ColumnInfo(name = "bid")
    @SerializedName("bid")
    val bid: Double = 0.0,

    @ColumnInfo(name = "ask")
    @SerializedName("ask")
    val ask: Double = 0.0
)