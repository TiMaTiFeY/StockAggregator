package dev.timatifey.stockaggregator.fragments.main.list.stocks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import dev.timatifey.stockaggregator.R
import dev.timatifey.stockaggregator.data.stocks.Stock
import dev.timatifey.stockaggregator.utils.PriceChanges
import dev.timatifey.stockaggregator.utils.withCurrency
import dev.timatifey.stockaggregator.viewmodel.stocks.StocksViewModel

class StocksAdapter(
    private val glide: RequestManager,
    private val stocksViewModel: StocksViewModel,
) : RecyclerView.Adapter<StocksAdapter.StockViewHolder>() {

    var stockList = emptyList<Stock>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_stock_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val currentItem = stockList[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.custom__name).text = currentItem.name
            findViewById<TextView>(R.id.custom__ticket).text = currentItem.ticker
            findViewById<TextView>(R.id.custom__current_price).text = currentItem.currentPrice
                .withCurrency(currentItem.currency)

            glide.load(currentItem.logo)
                .into(findViewById(R.id.custom__logo))

            if (position % 2 == 0) {
                background = ContextCompat.getDrawable(context, R.drawable.custom__background)
            }

            val changes = PriceChanges(
                currentItem.currentPrice,
                currentItem.previousClosePrice,
                currentItem.currency
            )
            val priceChangesView = findViewById<TextView>(R.id.custom__price_changes)
            priceChangesView.text = changes.viewString
            priceChangesView.setTextColor(
                if (changes.priceIsRaising)
                    ContextCompat.getColor(context, R.color.price_raises)
                else
                    ContextCompat.getColor(context, R.color.price_falls)
            )

            val likeButton = findViewById<AppCompatButton>(R.id.custom__like_btn)
            likeButton.backgroundTintList = ContextCompat.getColorStateList(
                context,
                if (currentItem.isFavourite) R.color.star_yellow else R.color.star_shadow
            )
            likeButton.setOnClickListener { button ->
                if (currentItem.isFavourite) {
                    currentItem.isFavourite = false
                    button.backgroundTintList = ContextCompat.getColorStateList(
                        context,
                        R.color.star_shadow
                    )
                } else {
                    currentItem.isFavourite = true
                    button.backgroundTintList = ContextCompat.getColorStateList(
                        context,
                        R.color.star_yellow
                    )
                }
                stocksViewModel.updateStock(currentItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return stockList.size
    }
}
