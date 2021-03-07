package dev.timatifey.stockaggregator.network

import com.google.gson.JsonArray
import dev.timatifey.stockaggregator.data.network.CompanyProfileResponse
import dev.timatifey.stockaggregator.data.network.QuoteResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FinnhubAPI {

    @GET("stock/symbol")
    suspend fun getStockSymbol(
        @Query("exchange") exchangeCode: String,
        @Query("token") token: String,
        //@Query("mic") filterByMicCode: String,
    ): JsonArray

    @GET("stock/profile2")
    suspend fun getCompanyProfile(
        @Query("symbol") symbol: String,
        @Query("token") token: String,
    ): CompanyProfileResponse

    @GET("quote")
    suspend fun getQuote(
        @Query("symbol") symbol: String,
        @Query("token") token: String,
    ): QuoteResponse
}