package dev.timatifey.stockaggregator.data.stocks

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Stock::class], version = 1, exportSchema = false)
abstract class StocksDatabase: RoomDatabase() {

    abstract fun stocksDao(): StocksDao

}