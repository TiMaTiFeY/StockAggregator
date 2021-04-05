package dev.timatifey.stockaggregator.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

import dev.timatifey.stockaggregator.R
import dev.timatifey.stockaggregator.viewmodel.search.SearchViewModel
import dev.timatifey.stockaggregator.viewmodel.stocks.StocksViewModel

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val stocksViewModel: StocksViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var searchEditText: AppCompatEditText
    private lateinit var searchIcon: AppCompatImageView
    private lateinit var backIcon: AppCompatImageButton
    private lateinit var clearIcon: AppCompatImageButton

    private lateinit var mainNavController: NavController

    private fun View.initViews() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.main_fragment__fragment) as NavHostFragment
        mainNavController = navHostFragment.navController

        searchEditText = findViewById(R.id.fragment_main__search_edit_text)
        searchIcon = findViewById(R.id.fragment_main__ic_search)
        backIcon = findViewById(R.id.fragment_main__ic_back)
        clearIcon = findViewById(R.id.fragment_main__ic_clear)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        view.initViews()
        searchEditText.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    searchEditText.hint = ""
                    navigateToSearch(searchEditText.text?.isEmpty())
                    searchIcon.visibility = View.GONE
                    backIcon.visibility = View.VISIBLE
                }
            }
            setOnEditorActionListener { v, actionId, _ ->
                val text = (v as AppCompatEditText).text
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (text.isNullOrEmpty()) {
                        clearIcon.visibility = View.GONE
                        mainNavController.navigate(R.id.searchEmptyFragment)
                    } else {
                        clearIcon.visibility = View.VISIBLE
                        mainNavController.navigate(R.id.searchResultFragment)
                    }
                    searchDatabase(text.toString())
                }
                false
            }
        }

        searchViewModel.searchRequest.observe(viewLifecycleOwner) {
            searchEditText.setText(it.searchText)
            clearIcon.visibility = View.VISIBLE
            mainNavController.navigate(R.id.searchResultFragment)
            searchDatabase(it.searchText)
        }

        backIcon.setOnClickListener {
            searchEditText.hint = getString(R.string.find_company_or_ticket)
            searchEditText.text?.clear()
            searchEditText.clearFocus()
            mainNavController.navigate(R.id.listFragment)

            searchIcon.visibility = View.VISIBLE
            backIcon.visibility = View.GONE
            clearIcon.visibility = View.GONE
        }

        clearIcon.setOnClickListener {
            searchEditText.text?.clear()
            clearSearchResult()
            navigateToSearch(true)
        }

        return view
    }

    private fun searchDatabase(text: String) {
        stocksViewModel.searchDatabase(text).observe(viewLifecycleOwner) {
            stocksViewModel.updateSearchList(it)
        }
    }

    private fun clearSearchResult() {
        stocksViewModel.updateSearchList(emptyList())
    }

    private fun navigateToSearch(fieldIsEmpty: Boolean?) {
        if (fieldIsEmpty == true) {
            mainNavController.navigate(R.id.searchEmptyFragment)
        } else {
            mainNavController.navigate(R.id.searchResultFragment)
        }
    }
}