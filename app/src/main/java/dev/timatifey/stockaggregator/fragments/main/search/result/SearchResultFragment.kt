package dev.timatifey.stockaggregator.fragments.main.search.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.timatifey.stockaggregator.R
import dev.timatifey.stockaggregator.fragments.main.list.ListItemDecoration
import dev.timatifey.stockaggregator.fragments.main.list.stocks.StocksAdapter
import dev.timatifey.stockaggregator.viewmodel.stocks.StocksViewModel

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private val stocksViewModel: StocksViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var showMoreBtn: AppCompatTextView
    private lateinit var adapter: StocksAdapter
    private lateinit var notFoundLabel: AppCompatTextView

    private fun View.initViews() {
        recyclerView = findViewById(R.id.fragment_search_result__recycler_view)
        adapter = StocksAdapter(Glide.with(requireActivity()), stocksViewModel)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(ListItemDecoration(8))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        notFoundLabel = findViewById(R.id.fragment_search_result__not_found_label)
        showMoreBtn = findViewById(R.id.fragment_search_result__show_more_btn)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main__search__result, container, false)
        view.initViews()

        stocksViewModel.searchResultList.observe(viewLifecycleOwner) {
            adapter.stockList = it
            if (it.isEmpty()) {
                notFoundLabel.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                notFoundLabel.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }

        return view
    }

}