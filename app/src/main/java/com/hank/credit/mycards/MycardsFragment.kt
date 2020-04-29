package com.hank.credit.mycards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hank.credit.R
import com.hank.credit.data.CardDatabase
import com.hank.credit.databinding.FragmentMycardsBinding

class MycardsFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentMycardsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_mycards,container,false
        )
        //database
        val application = requireNotNull(this.activity).application
        val cardDao = CardDatabase.getInstance(application).cardDao

        //ViewModel
        val viewModelFactory = MycardsViewModelFactory(cardDao)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(MycardsViewModel::class.java)

        //set My Card List
        val cardAdapter = MycardsAdapter()
        binding.mycardList.adapter = cardAdapter

        //Set My Tag List
        val tagLayoutManager = LinearLayoutManager(context)
        tagLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.mytagList.layoutManager = tagLayoutManager
        val tagAdapter = MycardsTagAdapter(viewModel)
        binding.mytagList.adapter = tagAdapter

        //Live Data
        viewModel.cardCoupon.observe(viewLifecycleOwner, Observer {
            cardAdapter.submitList(it)
        })
        viewModel.tag.observe(viewLifecycleOwner, Observer {
            tagAdapter.submitList(it)
        })
        return binding.root
    }
}