package com.hank.credit.search.detail

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.hank.credit.MainActivity
import com.hank.credit.R
import com.hank.credit.data.CardDao
import com.hank.credit.data.CardDatabase
import com.hank.credit.databinding.FragmentSearchDetailBinding

class SearchDetailFragment : Fragment(){

    lateinit var viewModel : SearchDetailViewModel
    lateinit var cardDao : CardDao
    lateinit var binding : FragmentSearchDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search_detail, container,false)

        //database
        val application = requireNotNull(this.activity).application
        cardDao = CardDatabase.getInstance(application).cardDao

        //viewModel
        val viewModelFactory = SearchDetailViewModelFactory(cardDao)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SearchDetailViewModel::class.java)

        //get Args , setCardCoupon
        val args = SearchDetailFragmentArgs.fromBundle(arguments!!)
        viewModel.setCardCoupon(args.cardName)
        viewModel.cardCoupon.observe(this, Observer {
            binding.cardCoupon = it
        })

        //button clicked
//        binding.detaliAddButton.setOnClickListener{
//            AlertDialog.Builder(context!!)
//                .setMessage("確定將${args.cardName}新增為我的卡片嗎?")
//                .setPositiveButton("確定",
//                    viewModel.PositiveButtonListener(args.cardName,binding)
//                )
//                .setNeutralButton("取消",null)
//                .show()
//        }

        setHasOptionsMenu(true)

        (activity as MainActivity).supportActionBar?.title = args.cardName
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_button,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val args = SearchDetailFragmentArgs.fromBundle(arguments!!)
        if(item.itemId == R.id.add_menu){
             AlertDialog.Builder(context!!)
                .setMessage("確定將${args.cardName}新增為我的卡片嗎?")
                .setPositiveButton("確定",
                    viewModel.PositiveButtonListener(args.cardName,binding)
                )
                .setNeutralButton("取消", null)
                .show()
                return true
        }else return super.onOptionsItemSelected(item)
    }

}

