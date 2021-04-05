package dev.timatifey.stockaggregator.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.timatifey.stockaggregator.data.database.StocksDao
import javax.inject.Singleton

import dev.timatifey.stockaggregator.data.database.AppDatabase
import dev.timatifey.stockaggregator.data.database.SearchDao

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideStocksDao(appDatabase: AppDatabase): StocksDao {
        return appDatabase.stocksDao()
    }

    @Singleton
    @Provides
    fun providesSearchDao(appDatabase: AppDatabase): SearchDao {
        return appDatabase.searchDao()
    }
}