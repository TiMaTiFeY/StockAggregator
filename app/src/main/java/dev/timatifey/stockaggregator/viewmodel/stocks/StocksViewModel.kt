package dev.timatifey.stockaggregator.viewmodel.stocks

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.timatifey.stockaggregator.Config
import dev.timatifey.stockaggregator.data.network.Status

import dev.timatifey.stockaggregator.data.stocks.Stock
import dev.timatifey.stockaggregator.repository.stocks.StocksRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class StocksViewModel @Inject constructor(
    private val stocksRepository: StocksRepository,
    application: Application
) : ViewModel() {

    val stocksList: LiveData<List<Stock>> = stocksRepository.readAllData
    val favouriteStocksList: LiveData<List<Stock>> = stocksRepository.favouriteStocksData
    val searchResultList: LiveData<List<Stock>> = stocksRepository.searchResultList

    private val _state = MutableLiveData<DataState>()
    val state: LiveData<DataState> = _state

    init {
        val sharedPreferences = application.applicationContext.getSharedPreferences(
            Config.APP_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )

        val isFirstOpen = sharedPreferences.getBoolean(Config.APP_IS_FIRST, true)
        if (isFirstOpen) {
            _state.value = DataState.LoadingState
            viewModelScope.launch(Dispatchers.IO) {
                val result = stocksRepository.loadStocks()
                launch(Dispatchers.Main) {
                    when (result.status) {
                        is Status.Success -> _state.value = DataState.SuccessState
                        is Status.Error -> _state.value =
                            result.message?.let { DataState.ErrorState(it) }
                    }
                }
            }
            sharedPreferences.edit()
                .putBoolean(Config.APP_IS_FIRST, false)
                .apply()
        }
    }

    fun updateStock(stock: Stock) {
        viewModelScope.launch(Dispatchers.IO) {
            stocksRepository.updateStock(stock)
        }
    }

    fun searchDatabase(request: String): LiveData<List<Stock>> {
        return stocksRepository.getSearchResult(request)
    }

    fun updateSearchList(list: List<Stock>) {
        stocksRepository.updateSearchResult(list)
    }

}

sealed class DataState {
    object LoadingState : DataState()
    object SuccessState : DataState()
    data class ErrorState(val error: String) : DataState()
}
