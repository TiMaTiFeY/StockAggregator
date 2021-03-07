package dev.timatifey.stockaggregator.data.network

import com.google.gson.annotations.SerializedName

data class StockSymbolResponse(
    @SerializedName("symbol")
    val symbol: String
)
