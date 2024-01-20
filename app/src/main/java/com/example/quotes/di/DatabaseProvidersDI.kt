package com.example.quotes.di

import android.content.Context
import androidx.room.Room
import com.example.quotes.db.QuoteDao
import com.example.quotes.db.QuoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseProvidersDI {

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        @ApplicationContext context: Context,
    ) =
        synchronized(this) {
            Room.databaseBuilder(
                context,
                QuoteDatabase::class.java,
                "Favorites DB"
            ).build()
        }


    @Singleton
    @Provides
    fun provideDao(database: QuoteDatabase): QuoteDao =
        database.dao()

}