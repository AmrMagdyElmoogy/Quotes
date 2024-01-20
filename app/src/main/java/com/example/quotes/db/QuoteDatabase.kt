package com.example.quotes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuoteTable::class], version = 1)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun dao(): QuoteDao

}