package dev.timatifey.stockaggregator.repository.stocks

import android.util.Log
import androidx.lifecycle.LiveData
import dev.timatifey.stockaggregator.data.network.Resource
import dev.timatifey.stockaggregator.data.network.Status
import dev.timatifey.stockaggregator.data.stocks.Stock
import dev.timatifey.stockaggregator.data.stocks.StocksDao
import dev.timatifey.stockaggregator.data.stocks.StocksDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StocksRepository @Inject constructor(
    private val stocksDao: StocksDao,
    private val stocksDataSource: StocksDataSource,
) {

    val readAllData: LiveData<List<Stock>> = stocksDao.readAllData()

    private suspend fun addStock(stock: Stock) {
        stocksDao.addStock(stock)
    }

    suspend fun loadStocks(): Resource<List<Stock>> {
        val result = stocksDataSource.loadStocks()
        if (result.status is Status.Success) {
            result.data!!.forEach {
                if (!containsSymbol(it.ticker)) {
                    addStock(it)
                }
            }
        }
        return result
    }

    suspend fun updateStock(stock: Stock) {
        stocksDao.updateStock(stock)
    }

    private fun containsSymbol(symbol: String): Boolean {
        val data = readAllData.value ?: return false
        for (stock in data) {
            if (stock.ticker == symbol) {
                return true
            }
        }
        return false
    }

}