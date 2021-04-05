package dev.timatifey.stockaggregator.repository.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.timatifey.stockaggregator.data.network.Resource
import dev.timatifey.stockaggregator.data.network.Status
import dev.timatifey.stockaggregator.data.model.Stock
import dev.timatifey.stockaggregator.data.database.StocksDao
import dev.timatifey.stockaggregator.data.sources.FinnhubDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StocksRepository @Inject constructor(
    private val stocksDao: StocksDao,
    private val finnhubDataSource: FinnhubDataSource,
) {

    val readAllData: LiveData<List<Stock>> = stocksDao.readAllData()
    val favouriteStocksData: LiveData<List<Stock>> = stocksDao.readFavouriteStocks()

    private val _searchResultList = MutableLiveData<List<Stock>>()
    val searchResultList: LiveData<List<Stock>> = _searchResultList

    private suspend fun addStock(stock: Stock) {
        stocksDao.addStock(stock)
    }

    suspend fun loadStocks(): Resource<List<Stock>> {
        val result = finnhubDataSource.loadStocks()
        if (result.status is Status.Success) {
            result.data!!.forEach {
                if (!containsSymbol(it.ticker)) {
                    addStock(it)
                }
            }
        }
        return result
    }

    fun getSearchResult(request: String): LiveData<List<Stock>> {
        return stocksDao.searchRequest("%$request%")
    }

    fun updateSearchResult(list: List<Stock>) {
        _searchResultList.value = list
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