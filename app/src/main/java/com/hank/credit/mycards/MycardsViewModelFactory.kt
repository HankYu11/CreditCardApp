package com.hank.credit.mycards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hank.credit.data.CardDao

class MycardsViewModelFactory(private val cardDao: CardDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MycardsViewModel::class.java)){
            return MycardsViewModel(cardDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}