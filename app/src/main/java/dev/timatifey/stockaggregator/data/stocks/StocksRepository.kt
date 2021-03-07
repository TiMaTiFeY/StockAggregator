package dev.timatifey.stockaggregator.data.stocks

import androidx.lifecycle.LiveData
import dev.timatifey.stockaggregator.data.network.Resource
import dev.timatifey.stockaggregator.data.network.Status
import kotlinx.coroutines.delay
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
                addStock(it)
            }
        }
        return result
    }

}