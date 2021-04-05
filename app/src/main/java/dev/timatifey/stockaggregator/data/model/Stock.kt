package dev.timatifey.stockaggregator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks_table")
data class Stock (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val ticker: String,
    val name: String,
    val logo: String,

    var currency: String,
    var currentPrice: Float,
    var previousClosePrice: Float,

    var isFavourite: Boolean = false,
)