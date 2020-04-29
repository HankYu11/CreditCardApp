package com.hank.credit.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hank.credit.data.CardDao

class SearchViewModelFactory(private val cardDao: CardDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(cardDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}