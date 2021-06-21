package com.example.paging.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {
    // 取得Dao操作方法
    abstract fun wordDao(): WordDao
    // 取得WordDatabase實體
    companion object{
        fun getDatabase(context: Context):WordDatabase{
            return Room.databaseBuilder(
                context,
                WordDatabase::class.java,
                "word_database"
            ).build()
        }

    }
}