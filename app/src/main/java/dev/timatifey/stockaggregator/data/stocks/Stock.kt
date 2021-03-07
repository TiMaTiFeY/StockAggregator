package dev.timatifey.stockaggregator.data.stocks

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks_table")
data class Stock (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val ticker: String,
    val name: String,
    val logo: String,

    val currency: String,
    val currentPrice: Float,
    val previousClosePrice: Float,

    val isFavourite: Boolean = false,
)