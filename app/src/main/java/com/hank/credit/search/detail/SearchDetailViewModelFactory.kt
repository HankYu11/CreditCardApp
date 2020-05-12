package com.hank.credit.search.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hank.credit.data.CardDao

class SearchDetailViewModelFactory(private val cardDao: CardDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchDetailViewModel::class.java)){
            return SearchDetailViewModel(cardDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}