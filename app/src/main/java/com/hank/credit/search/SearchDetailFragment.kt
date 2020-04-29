package com.hank.credit.search

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hank.credit.R
import com.hank.credit.data.CardDao
import com.hank.credit.data.CardDatabase
import com.hank.credit.databinding.FragmentSearchDetailBinding
import com.hank.credit.mycards.CardCoupon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.StringBuilder

class SearchDetailFragment : Fragment(){
    //此Fragment練習不使用ViewModel，造成程式碼可讀性較低且不易維護
    val uiScope = CoroutineScope(Dispatchers.Main)
    lateinit var cardCoupon : CardCoupon
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentSearchDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search_detail, container,false)

        //database
        val application = requireNotNull(this.activity).application
        val cardDao = CardDatabase.getInstance(application).cardDao

        //get Args
        val args = SearchDetailFragmentArgs.fromBundle(arguments!!)
        //set binding
        setCardCoupon(cardDao,args.cardName,binding)

        //button clicked
        binding.detaliAddButton.setOnClickListener{
            AlertDialog.Builder(context!!)
                .setMessage("確定將${args.cardName}新增為我的卡片嗎?")
                .setPositiveButton("確定", PositiveButtonListener(args.cardName,cardDao))
                .setNeutralButton("取消",null)
                .show()
        }

        return binding.root
    }
    
    private fun setCardCoupon(cardDao: CardDao,cardName: String,binding : FragmentSearchDetailBinding){
        uiScope.launch {
            cardCoupon = getCardCoupon(cardDao,cardName)
            binding.cardCoupon = cardCoupon
        }
    }

    private suspend fun getCardCoupon(cardDao : CardDao,cardName : String) : CardCoupon{
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
}

class PositiveButtonListener(val cardName : String,val cardDao: CardDao): DialogInterface.OnClickListener{
    val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onClick(dialog: DialogInterface?, which: Int) {
        onAddClicked(cardName)
    }
    //setCardMine
    fun onAddClicked(cardName : String){
        uiScope.launch{
            setCardMine(cardName)
        }
    }
    private suspend fun setCardMine(cardName: String) {
        withContext(Dispatchers.IO){
            cardDao.setCardMine(cardName)
        }
    }
}