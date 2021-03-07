package dev.timatifey.stockaggregator.data.network

import com.google.gson.annotations.SerializedName

data class CompanyProfileResponse(
    @SerializedName("currency")
    val currency: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("ticker")
    val ticker: String,

    @SerializedName("logo")
    val logo: String,

    @SerializedName("weburl")
    val webUrl: String,
)
