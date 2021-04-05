package dev.timatifey.stockaggregator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_table")
data class SearchRequest (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val searchText: String,
)