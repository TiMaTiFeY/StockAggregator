package dev.timatifey.stockaggregator.fragments.main.list.favourite

import android.os.Bundle
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
import dev.timatifey.stockaggregator.viewmodel.favourite.FavouriteViewModel

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private lateinit var adapter: FavouriteAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyLabel: TextView

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.fragment_favourite__recycler_view)
        adapter = FavouriteAdapter(Glide.with(requireActivity()), favouriteViewModel)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(ListItemDecoration(8))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        emptyLabel = view.findViewById(R.id.fragment_favourite__empty_label)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)
        initViews(view)

        favouriteViewModel.favouriteList.observe(viewLifecycleOwner, {
            adapter.favouriteList = it
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