package dev.timatifey.stockaggregator.fragments.main.list.favourite

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
import dev.timatifey.stockaggregator.viewmodel.stocks.StocksViewModel

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private val stocksViewModel: StocksViewModel by viewModels()
    private lateinit var adapter: FavouriteAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyLabel: AppCompatTextView

    private fun View.initViews() {
        recyclerView = findViewById(R.id.fragment_favourite__recycler_view)
        adapter = FavouriteAdapter(Glide.with(requireActivity()), stocksViewModel)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(ListItemDecoration(8))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        emptyLabel = findViewById(R.id.fragment_favourite__empty_label)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main__list__favourite, container, false)
        view.initViews()

        stocksViewModel.favouriteStocksList.observe(viewLifecycleOwner, {
            adapter.stockList = it
            if (it.isEmpty()) {
                emptyLabel.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyLabel.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        })

        return view
    }

}