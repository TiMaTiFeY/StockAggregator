package dev.timatifey.stockaggregator.data.sources

import android.util.Log
import com.google.gson.Gson
import dev.timatifey.stockaggregator.Config.Companion.CLEARBIT_BASE_URL
import dev.timatifey.stockaggregator.Config.Companion.FINNHUB_BASE_SYMBOLS
import dev.timatifey.stockaggregator.Config.Companion.FINNHUB_TOKEN
import dev.timatifey.stockaggregator.data.model.SearchRequest
import dev.timatifey.stockaggregator.data.network.Resource
import dev.timatifey.stockaggregator.data.network.ResponseHandler
import dev.timatifey.stockaggregator.data.network.Status
import dev.timatifey.stockaggregator.data.network.StockSymbolResponse
import dev.timatifey.stockaggregator.data.model.Stock
import dev.timatifey.stockaggregator.network.FinnhubAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FinnhubDataSource @Inject constructor(
    private val finnhubAPI: FinnhubAPI
) {
    companion object {
        const val TAG = "StocksDataSource"
    }

    suspend fun loadStocks(): Resource<List<Stock>> {
        return try {
//            val stockSymbol = getStockSymbol("ME")
//            if (stockSymbol.status is Status.Error) {
//                throw Exception("Stock symbol error")
//            }
//            Log.i(TAG, stockSymbol.data!!.toString())
            val stocks = mutableListOf<Stock>()
            for (symbol in FINNHUB_BASE_SYMBOLS) {
                val stock = getStock(symbol)
                if (stock.status is Status.Error) {
                    throw Exception("Stock by symbol[${symbol}] error")
                }
                Log.i(TAG, stock.toString())
                stocks.add(stock.data!!)
            }
            ResponseHandler.handleSuccess(stocks)
        } catch (e: Exception) {
            ResponseHandler.handleException(e)
        }
    }

    private suspend fun getStockSymbol(code: String): Resource<List<String>> {
        return try {
            val jsonArray = finnhubAPI.getStockSymbol(code, FINNHUB_TOKEN)
            val gson = Gson()
            val symbols = mutableListOf<String>()
            jsonArray.forEach {
                symbols.add(gson.fromJson(it, StockSymbolResponse::class.java).symbol)
            }
            ResponseHandler.handleSuccess(symbols)
        } catch (e: Exception) {
            ResponseHandler.handleException(e)
        }
    }

    private suspend fun getStock(symbol: String): Resource<Stock> {
        return try {
            val companyProfile = finnhubAPI.getCompanyProfile(symbol, FINNHUB_TOKEN)
            val quote = finnhubAPI.getQuote(symbol, FINNHUB_TOKEN)
            val stock = Stock(
                ticker = companyProfile.ticker,
                name = companyProfile.name,
                logo = CLEARBIT_BASE_URL + parseUrl(companyProfile.webUrl, companyProfile.currency),
                currency = companyProfile.currency,
                currentPrice = quote.currentPrice,
                previousClosePrice = quote.previousPrice
            )
            ResponseHandler.handleSuccess(stock)
        } catch (e: Exception) {
            ResponseHandler.handleException(e)
        }
    }

    private fun parseUrl(webUrl: String, currency: String): String {
        var url = webUrl
            .replaceFirst("https://", "")
            .replaceFirst("http://", "")
            .split("/")
            .first()
        if (currency == "RUB") {
            url = url.replace(".com", ".ru")
        }
        return url
    }

    fun loadPopularRequests(): Resource<List<SearchRequest>> {
        return try {
            val requests = listOf(
                "Apple",
                "Amazon",
                "Google",
                "Tesla",
                "First Solar",
                "Alibaba",
                "Facebook",
                "Mastercard"
            )
                .map { SearchRequest(searchText = it) }
            ResponseHandler.handleSuccess(requests)
        } catch (e: Exception) {
            ResponseHandler.handleException(e)
        }
    }

}
