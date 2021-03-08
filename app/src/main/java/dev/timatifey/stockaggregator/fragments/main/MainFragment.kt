package dev.timatifey.stockaggregator.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
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

    private lateinit var searchEditText: EditText
    private lateinit var searchIcon: ImageView
    private lateinit var backIcon: ImageView
    private lateinit var clearIcon: ImageView

    private lateinit var mainNavController: NavController

    private fun initViews(view: View) {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.main_fragment__fragment) as NavHostFragment
        mainNavController = navHostFragment.navController

        searchEditText = view.findViewById(R.id.fragment_main__search_edit_text)
        searchIcon = view.findViewById(R.id.fragment_main__ic_search)
        backIcon = view.findViewById(R.id.fragment_main__ic_back)
        clearIcon = view.findViewById(R.id.fragment_main__ic_clear)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        initViews(view)
        searchEditText.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    searchEditText.hint = ""
                    navigateToSearch(searchEditText.text.isEmpty())
                    searchIcon.visibility = View.GONE
                    backIcon.visibility = View.VISIBLE
                }
            }

            doOnTextChanged { text, start, before, count ->
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
            searchEditText.text.clear()
            searchEditText.clearFocus()
            mainNavController.navigate(R.id.listFragment)

            searchIcon.visibility = View.VISIBLE
            backIcon.visibility = View.GONE
            clearIcon.visibility = View.GONE
        }

        clearIcon.setOnClickListener {
            searchEditText.text.clear()
        }

        return view
    }

    private fun searchDatabase(text: String) {
        stocksViewModel.searchDatabase(text).observe(viewLifecycleOwner) {
            stocksViewModel.updateSearchList(it)
        }
    }

    private fun navigateToSearch(fieldIsEmpty: Boolean) {
        if (fieldIsEmpty) {
            mainNavController.navigate(R.id.searchEmptyFragment)
        } else {
            mainNavController.navigate(R.id.searchResultFragment)
        }
    }
}