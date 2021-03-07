package dev.timatifey.stockaggregator.viewmodel.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

import dev.timatifey.stockaggregator.data.stocks.Stock
import dev.timatifey.stockaggregator.repository.favourite.FavouriteRepository


@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favouriteRepository: FavouriteRepository,
    application: Application
): AndroidViewModel(application) {

    val favouriteList: LiveData<List<Stock>> = favouriteRepository.favouriteStocksData

    fun deleteFromFavourite(stock: Stock) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteRepository.deleteFromFavourite(stock)
        }
    }
}

