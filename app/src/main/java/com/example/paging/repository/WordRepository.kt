package com.example.paging.repository

import com.example.paging.data.Word
import com.example.paging.data.WordDao
import com.example.paging.data.WordDatabase


class WordRepository(private val dao: WordDao) {
    fun getAllWord() = dao.getAllWord()

    suspend fun insertWord(word: Word){
        dao.insertWord(word)
    }
}