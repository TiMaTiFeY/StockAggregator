package dev.timatifey.stockaggregator.fragments.main.search.empty

import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.timatifey.stockaggregator.R
import dev.timatifey.stockaggregator.data.model.SearchRequest
import dev.timatifey.stockaggregator.fragments.main.list.ListItemDecoration
import dev.timatifey.stockaggregator.viewmodel.search.SearchViewModel

@AndroidEntryPoint
class SearchEmptyFragment : Fragment(), SearchAdapter.Listener {

    private val searchViewModel: SearchViewModel by viewModels(
        { requireParentFragment().requireParentFragment() }
    )

    private lateinit var recyclerViewPopularRequests: RecyclerView
    private lateinit var recyclerViewSearchedRequests: RecyclerView

    private lateinit var adapterPopular: SearchAdapter
    private lateinit var adapterSearched: SearchAdapter

    private lateinit var emptyLabelPopular: AppCompatTextView
    private lateinit var labelSearched: AppCompatTextView

    private fun View.initViews() {
        recyclerViewPopularRequests =
            findViewById(R.id.fragment_main__search__empty__recycler_view__popular_requests)
        recyclerViewSearchedRequests =
            findViewById(R.id.fragment_main__search__empty__recycler_view__you_searched)

        adapterPopular = SearchAdapter(this@SearchEmptyFragment)
        adapterSearched = SearchAdapter(this@SearchEmptyFragment)

        recyclerViewSearchedRequests.adapter = adapterSearched
        recyclerViewSearchedRequests.addItemDecoration(ChipItemDecoration())
        recyclerViewSearchedRequests.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)

        recyclerViewPopularRequests.adapter = adapterPopular
        recyclerViewPopularRequests.addItemDecoration(ChipItemDecoration())
        recyclerViewPopularRequests.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)

        emptyLabelPopular =
            findViewById(R.id.fragment_main__search__empty__label__popular_requests_empty)
        labelSearched =
            findViewById(R.id.fragment_main__search__empty__label__you_searched)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main__search__empty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.initViews()
        searchViewModel.loadPopularRequests()

        searchViewModel.myRequests.observe(viewLifecycleOwner) {
            adapterSearched.searchRequestsList = it
            if (it.isEmpty()) {
                labelSearched.visibility = View.GONE
                recyclerViewSearchedRequests.visibility = View.GONE
            } else {
                labelSearched.visibility = View.VISIBLE
                recyclerViewSearchedRequests.visibility = View.VISIBLE
            }
        }

        searchViewModel.popularRequests.observe(viewLifecycleOwner) {
            adapterPopular.searchRequestsList = it
            if (it.isEmpty()) {
                emptyLabelPopular.visibility = View.VISIBLE
                recyclerViewPopularRequests.visibility = View.GONE
            } else {
                emptyLabelPopular.visibility = View.GONE
                recyclerViewPopularRequests.visibility = View.VISIBLE
            }
        }
    }

    override fun onRequestClick(request: SearchRequest) {
        searchViewModel.addRequestToSearchView(request)
    }

    class ChipItemDecoration : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.right = 4
            outRect.left = 4
            outRect.top = 8
            outRect.bottom = 8
        }

    }


}