package dev.timatifey.stockaggregator.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.timatifey.stockaggregator.data.model.SearchRequest

@Dao
interface SearchDao {

    @Query("SELECT * FROM search_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<SearchRequest>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addSearchRequest(searchRequestRequest: SearchRequest)
}