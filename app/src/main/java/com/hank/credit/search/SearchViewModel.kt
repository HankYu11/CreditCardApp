package com.hank.credit.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hank.credit.data.Card
import com.hank.credit.data.CardDao
import com.hank.credit.ioThread
import kotlinx.coroutines.*

class SearchViewModel(val cardDao: CardDao) : ViewModel(){
    val TAG = "SearchViewModel"

    private val _cards = MutableLiveData<List<Card>>()
    val cards : LiveData<List<Card>>
    get() = _cards

    private val _tag = MutableLiveData<List<String>>()
    val tag : LiveData<List<String>>
    get() = _tag

    val uiScope = CoroutineScope(Dispatchers.Main)

    init {
        initializeTag()
        initializeCard()
    }

    fun initializeCard() {
        uiScope.launch {
            _cards.value = getCard()
        }
    }

    private suspend fun getCard(): List<Card> {
        return withContext(Dispatchers.IO){
            val cardList = cardDao.getAllCards()
            cardList
        }
    }

    private fun initializeTag(){
        uiScope.launch {
            _tag.value = getType()
        }
    }

    private suspend fun getType() : List<String>{
        return withContext(Dispatchers.IO){
            val types = cardDao.getAllType()
            types
        }
    }

    fun onTagClicked(type : String){
        uiScope.launch {
            val newCardNameList = getNewCardNameList(type)
            val newCardList = getNewCardList(newCardNameList)
            _cards.value = newCardList
        }
    }

    private suspend fun getNewCardList(cardNameList : List<String>) : List<Card>{
        return withContext(Dispatchers.IO){
            val newCardList = mutableListOf<Card>()
            cardNameList.forEach {
                newCardList.add(cardDao.getCard(it))
            }
            newCardList
        }
    }

    private suspend fun getNewCardNameList(type : String) : List<String>{
        return withContext(Dispatchers.IO){
            cardDao.getTypeCardName(type)
        }
    }
}