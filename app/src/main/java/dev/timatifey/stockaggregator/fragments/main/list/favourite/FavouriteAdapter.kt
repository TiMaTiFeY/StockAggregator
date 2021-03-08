package dev.timatifey.stockaggregator.fragments.main.list.favourite

import com.bumptech.glide.RequestManager

import dev.timatifey.stockaggregator.data.stocks.Stock
import dev.timatifey.stockaggregator.fragments.main.list.stocks.StocksAdapter
import dev.timatifey.stockaggregator.viewmodel.stocks.StocksViewModel

open class FavouriteAdapter(
    glide: RequestManager,
    private val stocksViewModel: StocksViewModel,
) : StocksAdapter(glide, stocksViewModel) {

    override fun afterLikeClick(currentItem: Stock) {
        stocksViewModel.updateStock(currentItem)
        notifyDataSetChanged()
    }
}