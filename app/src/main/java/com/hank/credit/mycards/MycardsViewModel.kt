package com.hank.credit.mycards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hank.credit.data.Card
import com.hank.credit.data.CardDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.StringBuilder

class MycardsViewModel(val cardDao : CardDao) : ViewModel(){

    private val _tag = MutableLiveData<List<String>>()
    val tag : LiveData<List<String>>
        get() = _tag

    private val _cardCoupon = MutableLiveData<List<CardCoupon>>()
    val cardCoupon : LiveData<List<CardCoupon>>
    get() = _cardCoupon

    val uiScope = CoroutineScope(Dispatchers.Main)

    init {
        initializeTag()
        initializeCardCoupon()
    }

    fun initializeCardCoupon(){
        uiScope.launch {
            _cardCoupon.value = getMyCardCoupon()
        }
    }

    private suspend fun getMyCardCoupon() : List<CardCoupon>{
        return withContext(Dispatchers.IO){
            val mycards = cardDao.getMyCards()
            val myCardCoupon = mutableListOf<CardCoupon>()
            mycards.forEach { card ->
                val myCoupon = cardDao.getCoupon(card.cardName)
                val description = StringBuilder()
                myCoupon.forEach {
                    description.append("${it.couponType} : ${it.couponDiscount} \n")
                }
                val cardCoupon = CardCoupon(card.cardName, card.cardImg,description.toString())
                myCardCoupon.add(cardCoupon)
            }
            myCardCoupon
        }
    }

    private fun initializeTag(){
        uiScope.launch {
            _tag.value = getMyType()
        }
    }

    private suspend fun getMyType() : List<String>{
        return withContext(Dispatchers.IO){
            val types = cardDao.getMyType()
            types
        }
    }

    fun onMyTagClicked(type : String){
        uiScope.launch {
            val tagCardNameList = getNewCardNameList(type)
            val newCardCouponList = getNewCardCouponList(tagCardNameList)
            _cardCoupon.value = newCardCouponList
        }
    }

    private suspend fun getNewCardCouponList(cardNameList : List<String>) : List<CardCoupon>{
        return withContext(Dispatchers.IO){
            val myCardCoupon = mutableListOf<CardCoupon>()
            cardNameList.forEach { cardName ->
                val myCard = cardDao.getCard(cardName)
                val myCoupon = cardDao.getCoupon(cardName)
                val description = StringBuilder()
                myCoupon.forEach {
                    description.append("${it.couponType} : ${it.couponDiscount} \n")
                }
                val cardCoupon = CardCoupon(myCard.cardName,myCard.cardImg,description.toString())
                myCardCoupon.add(cardCoupon)
            }
            myCardCoupon
        }
    }

    private suspend fun getNewCardNameList(type : String) : List<String>{
        return withContext(Dispatchers.IO){
            cardDao.getTypeMyCardName(type)
        }
    }
}

data class CardCoupon(var cardName : String, var cardImg : Int, var cardDescription : String)