package com.example.quotes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuoteTable::class], version = 1)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun dao(): QuoteDao

    companion object {
        @Volatile
        private var instance: QuoteDatabase? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = createDatabase(context)
            }
        }

        fun getInstance(): QuoteDatabase {
            return instance
                ?: throw IllegalAccessException("You cannot access database before initialize it first")
        }

        private fun createDatabase(context: Context): QuoteDatabase {
            return synchronized(this) {
                Room.databaseBuilder(
                    context,
                    QuoteDatabase::class.java,
                    "Favorites DB"
                ).build()
            }

        }
    }
}