package dev.timatifey.stockaggregator.repository.stocks

import androidx.lifecycle.LiveData
import dev.timatifey.stockaggregator.data.database.SearchDao
import dev.timatifey.stockaggregator.data.model.SearchRequest
import dev.timatifey.stockaggregator.data.network.Resource
import dev.timatifey.stockaggregator.data.sources.FinnhubDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val searchDao: SearchDao,
    private val finnhubDataSource: FinnhubDataSource,
) {
    val readAllData: LiveData<List<SearchRequest>> = searchDao.readAllData()

    suspend fun addRequest(searchRequestRequest: SearchRequest) {
        searchDao.addSearchRequest(searchRequestRequest)
    }

    suspend fun loadPopularRequests(): Resource<List<SearchRequest>> {
        return finnhubDataSource.loadPopularRequests()
    }
}