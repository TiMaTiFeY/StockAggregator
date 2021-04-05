package dev.timatifey.stockaggregator.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.timatifey.stockaggregator.data.model.SearchRequest
import dev.timatifey.stockaggregator.data.model.Stock

@Database(entities = [Stock::class, SearchRequest::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun stocksDao(): StocksDao

    abstract fun searchDao(): SearchDao

}