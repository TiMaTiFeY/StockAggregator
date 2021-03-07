package dev.timatifey.stockaggregator.viewmodel.stocks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel

import dev.timatifey.stockaggregator.data.network.Status
import dev.timatifey.stockaggregator.data.stocks.Stock
import dev.timatifey.stockaggregator.repository.stocks.StocksRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksViewModel @Inject constructor(
    private val stocksRepository: StocksRepository,
    application: Application
): AndroidViewModel(application) {

    val stocksList: LiveData<List<Stock>> = stocksRepository.readAllData

    private val _state = MutableLiveData<DataState>()
    val state: LiveData<DataState> = _state

    init {
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
    }

    fun updateStock(stock: Stock) {
        viewModelScope.launch(Dispatchers.IO) {
            stocksRepository.updateStock(stock)
        }
    }
}

sealed class DataState {
    object LoadingState: DataState()
    object SuccessState: DataState()
    data class ErrorState(val error: String): DataState()
}
