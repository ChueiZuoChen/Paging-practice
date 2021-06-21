package com.example.paging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.paging.data.Word
import com.example.paging.data.WordDao
import com.example.paging.repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val dao: WordDao) : ViewModel() {

    //1.從db哪裡拿資料？ -> PagingSource<Int,Word>
    fun getWords(): PagingSource<Int, Word> {
        return WordRepository(dao).getAllWord()
    }

    fun insertWord(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        dao.insertWord(word)
    }
    //123
    //2.把資料從db拿出來 -> PagingData<T>
    //注意：因為把資料已經從Source拿出來了,
    //所以要透過Flow把資料傳出去給MainActivity使用
    //其回傳型態就要變成可以用的資料 Paging的 DiffUtil.ItemCallback<Word>() 去處理新舊的資料

    fun getStream():Flow<PagingData<Word>>{
        val pager = Pager(
            config = PagingConfig(pageSize = 20),
            initialKey = 0
        ){
            getWords()
        }.flow

        return pager
    }

}

