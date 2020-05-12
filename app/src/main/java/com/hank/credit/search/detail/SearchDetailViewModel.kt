package com.hank.credit.search.detail

import android.content.DialogInterface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.hank.credit.data.CardDao
import com.hank.credit.databinding.FragmentSearchDetailBinding
import com.hank.credit.mycards.CardCoupon
import com.hank.credit.search.SearchFragmentDirections
import kotlinx.coroutines.*
import java.lang.StringBuilder

class SearchDetailViewModel(val cardDao: CardDao) : ViewModel(){

    private val _cardCoupon = MutableLiveData<CardCoupon>()
    val cardCoupon : LiveData<CardCoupon>
    get() = _cardCoupon

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    fun setCardCoupon(cardName : String){
        uiScope.launch {
            _cardCoupon.value = getCardCouponfromDatabase(cardName)
        }
    }

    private suspend fun getCardCouponfromDatabase(cardName: String) : CardCoupon {
        return withContext(Dispatchers.IO){
            val myCard = cardDao.getCard(cardName)
            val myCoupon = cardDao.getCoupon(cardName)
            val description = StringBuilder()
            myCoupon.forEach {
                description.append("${it.couponType} : ${it.couponDiscount} \n")
            }
            val cardCoupon = CardCoupon(myCard.cardName,myCard.cardImg,description.toString())
            cardCoupon
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    inner class PositiveButtonListener(val cardName: String, val binding : FragmentSearchDetailBinding): DialogInterface.OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            uiScope.launch{
                setCardMine(cardName)
                binding.root.findNavController().navigate(SearchDetailFragmentDirections.actionSearchDetailFragmentToSearchFragment())
            }
        }
        private suspend fun setCardMine(cardName: String) {
            withContext(Dispatchers.IO){
                cardDao.setCardMine(cardName)
            }
        }
    }


}