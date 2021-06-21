package com.example.paging.data


import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface WordDao {
    @Insert
    suspend fun insertWord(word: Word)

    @Query("Select * from word_table")
    fun getAllWord(): PagingSource<Int, Word>
}