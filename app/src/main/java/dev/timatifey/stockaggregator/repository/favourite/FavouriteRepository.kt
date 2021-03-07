package dev.timatifey.stockaggregator.repository.favourite

import androidx.lifecycle.LiveData
import dev.timatifey.stockaggregator.data.stocks.Stock
import dev.timatifey.stockaggregator.data.stocks.StocksDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteRepository @Inject constructor(
    private val stocksDao: StocksDao
) {

    val favouriteStocksData: LiveData<List<Stock>> = stocksDao.readFavouriteStocks()

    suspend fun deleteFromFavourite(stock: Stock) {
        stocksDao.updateStock(stock)
    }
}