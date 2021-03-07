package dev.timatifey.stockaggregator.utils

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class PriceChanges(currentPrice: Float, previousClosePrice: Float, currency: String) {
    var viewString: String? = null
    var priceIsRaising = false

    init {
        priceIsRaising = currentPrice >= previousClosePrice
        val dif = String.format("%.2f", abs(currentPrice - previousClosePrice))
        val proc = String.format(
            "%.2f", (max(currentPrice, previousClosePrice) /
                    min(currentPrice, previousClosePrice) - 1) * 100
        )
        viewString = "${if (priceIsRaising) "+" else "-"}${dif.withCurrency(currency)} ($proc%)"
    }
}