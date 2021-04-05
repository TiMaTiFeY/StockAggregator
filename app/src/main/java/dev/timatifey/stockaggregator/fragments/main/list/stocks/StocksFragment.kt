package dev.timatifey.stockaggregator.fragments.main.list.stocks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

import dev.timatifey.stockaggregator.R
import dev.timatifey.stockaggregator.fragments.main.list.ListItemDecoration
import dev.timatifey.stockaggregator.viewmodel.stocks.DataState
import dev.timatifey.stockaggregator.viewmodel.stocks.StocksViewModel

@AndroidEntryPoint
class StocksFragment : Fragment() {

    private val stockViewModel: StocksViewModel by viewModels()
    private lateinit var adapter: StocksAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressLoader: ProgressBar

    private fun View.initViews() {
        recyclerView = findViewById(R.id.fragment_stocks__recycler_view)
        adapter = StocksAdapter(Glide.with(requireActivity()), stockViewModel)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(ListItemDecoration(8))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        progressLoader = findViewById(R.id.fragment_stocks__progress_bar)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main__list__stocks, container, false)

        view.initViews()

        stockViewModel.stocksList.observe(viewLifecycleOwner, {
            adapter.stockList = it
        })

        stockViewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                is DataState.LoadingState -> {
                    progressLoader.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                is DataState.SuccessState -> {
                    progressLoader.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
                is DataState.ErrorState -> {
                    recyclerView.visibility = View.GONE
                    progressLoader.visibility = View.GONE
                    Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                }
            }
        })

        return view
    }

}