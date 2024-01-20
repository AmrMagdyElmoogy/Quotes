package com.example.quotes.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Query("Select * from favorites")
    fun getAllFavorites(): Flow<List<QuoteTable>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewQuoteToDB(quoteModel: QuoteTable)

    @Delete(entity = QuoteTable::class)
    suspend fun removeQuote(quoteModel: QuoteTable)


}