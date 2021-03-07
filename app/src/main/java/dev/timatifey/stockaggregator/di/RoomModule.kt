package dev.timatifey.stockaggregator.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.timatifey.stockaggregator.data.stocks.StocksDao
import javax.inject.Singleton

import dev.timatifey.stockaggregator.data.stocks.StocksDatabase

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideStocksDao(@ApplicationContext context: Context): StocksDao {
        return Room.databaseBuilder(
            context,
            StocksDatabase::class.java,
            "company_database"
        ).build().stocksDao()
    }

}