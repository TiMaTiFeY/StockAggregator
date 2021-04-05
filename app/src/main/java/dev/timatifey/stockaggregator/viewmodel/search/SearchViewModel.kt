package dev.timatifey.stockaggregator.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.timatifey.stockaggregator.data.model.SearchRequest
import dev.timatifey.stockaggregator.data.network.Status
import dev.timatifey.stockaggregator.repository.stocks.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    val myRequests: LiveData<List<SearchRequest>> = searchRepository.readAllData

    private val _popularRequests = MutableLiveData<List<SearchRequest>>()
    val popularRequests: LiveData<List<SearchRequest>> = _popularRequests

    private val _searchRequest = MutableLiveData<SearchRequest>()
    val searchRequest: LiveData<SearchRequest> = _searchRequest

    fun loadPopularRequests() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = searchRepository.loadPopularRequests()
            withContext(Dispatchers.Main) {
                if (result.status is Status.Success) {
                    _popularRequests.value = result.data
                }
            }
        }
    }

    fun addRequestToSearchView(request: SearchRequest) {
        _searchRequest.value = request
    }

}