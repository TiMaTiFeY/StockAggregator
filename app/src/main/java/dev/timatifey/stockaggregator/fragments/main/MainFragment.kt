package dev.timatifey.stockaggregator.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

import dev.timatifey.stockaggregator.R
import dev.timatifey.stockaggregator.viewmodel.stocks.StocksViewModel

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val stocksViewModel: StocksViewModel by viewModels()

    private lateinit var searchEditText: AppCompatEditText
    private lateinit var searchIcon: AppCompatImageView
    private lateinit var backIcon: AppCompatImageView
    private lateinit var clearIcon: AppCompatImageView

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

            doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty()) {
                    clearIcon.visibility = View.GONE
                    mainNavController.navigate(R.id.searchEmptyFragment)
                } else {
                    clearIcon.visibility = View.VISIBLE
                    mainNavController.navigate(R.id.searchResultFragment)
                }
                searchDatabase(text.toString())
            }
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
        }

        return view
    }

    private fun searchDatabase(text: String) {
        stocksViewModel.searchDatabase(text).observe(viewLifecycleOwner) {
            stocksViewModel.updateSearchList(it)
        }
    }

    private fun navigateToSearch(fieldIsEmpty: Boolean?) {
        if (fieldIsEmpty == true) {
            mainNavController.navigate(R.id.searchEmptyFragment)
        } else {
            mainNavController.navigate(R.id.searchResultFragment)
        }
    }
}