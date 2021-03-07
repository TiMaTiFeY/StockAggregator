package dev.timatifey.stockaggregator.data.stocks

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StocksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStock(stock: Stock)

    @Query("SELECT * FROM stocks_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Stock>>

}