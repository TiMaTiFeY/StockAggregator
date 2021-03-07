package dev.timatifey.stockaggregator.data.network

import com.google.gson.annotations.SerializedName

data class QuoteResponse(
    @SerializedName("c")
    val currentPrice: Float,

    @SerializedName("h")
    val highPrice: Float,

    @SerializedName("l")
    val lowPrice: Float,

    @SerializedName("o")
    val openPrice: Float,

    @SerializedName("pc")
    val previousPrice: Float,
)
