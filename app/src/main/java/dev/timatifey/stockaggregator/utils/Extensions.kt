package dev.timatifey.stockaggregator.utils

fun Any.withCurrency(currency: String): CharSequence =
    when (currency) {
        "USD" -> "$$this"
        "RUB" -> "$this ₽"
        "EUR" -> "$this €"
        else -> "$this $currency"
    }