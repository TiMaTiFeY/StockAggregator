package dev.timatifey.stockaggregator.fragments.main.search.result

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private lateinit var showMoreBtn: TextView
    private lateinit var adapter: StocksAdapter
    private lateinit var notFoundLabel: TextView

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.fragment_search_result__recycler_view)
        adapter = StocksAdapter(Glide.with(requireActivity()), stocksViewModel)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(ListItemDecoration(8))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        notFoundLabel = view.findViewById(R.id.fragment_search_result__not_found_label)
        showMoreBtn = view.findViewById(R.id.fragment_search_result__show_more_btn)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_result, container, false)
        initViews(view)

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