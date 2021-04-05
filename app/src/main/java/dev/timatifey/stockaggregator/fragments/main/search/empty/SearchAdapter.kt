package dev.timatifey.stockaggregator.fragments.main.search.empty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import dev.timatifey.stockaggregator.R
import dev.timatifey.stockaggregator.data.model.SearchRequest

class SearchAdapter(
    private val onRequestClickListener: Listener
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    var searchRequestsList = emptyList<SearchRequest>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRequest: AppCompatTextView =
            itemView.findViewById(R.id.list_item__chip_search_request_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item__chip__search_request, parent, false)
        view.setOnClickListener {
            onRequestClickListener.onRequestClick(it.tag as SearchRequest)
        }
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = searchRequestsList[position]
        holder.tvRequest.text = currentItem.searchText
        holder.itemView.tag = currentItem
    }

    override fun getItemCount(): Int {
        return searchRequestsList.size
    }

    interface Listener {
        fun onRequestClick(request: SearchRequest)
    }
}