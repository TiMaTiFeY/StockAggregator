package dev.timatifey.stockaggregator.fragments.stocks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import dev.timatifey.stockaggregator.R
import dev.timatifey.stockaggregator.data.stocks.Stock
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class StocksAdapter(
    private val glide: RequestManager
    ): RecyclerView.Adapter<StocksAdapter.StockViewHolder>() {

    var stockList = emptyList<Stock>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class StockViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_stock_row, parent, false))
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
        }
    }

    class PriceChanges(currentPrice: Float, previousClosePrice: Float, currency: String) {
        var viewString: String? = null
        var priceIsRaising = false

        init {
            priceIsRaising = currentPrice >= previousClosePrice
            val dif = abs(currentPrice - previousClosePrice)
            val proc = String.format("%.2f", (max(currentPrice, previousClosePrice) /
                    min(currentPrice, previousClosePrice) - 1) * 100)
            viewString = "${if (priceIsRaising) "+" else "-"}${dif.withCurrency(currency)} ($proc%)"
        }
    }

    override fun getItemCount(): Int {
        return stockList.size
    }
}

private fun Number.withCurrency(currency: String): CharSequence =
    when(currency) {
        "USD" -> "$$this"
        "RUB" -> "$this ₽"
        "EUR" -> "$this €"
        else -> "$this $currency"
    }
