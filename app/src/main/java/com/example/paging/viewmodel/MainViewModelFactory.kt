package com.example.paging.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paging.data.WordDatabase
import java.lang.IllegalArgumentException


class MainViewModelFactory(val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val dao = WordDatabase.getDatabase(application.applicationContext).wordDao()
            return MainViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }


}