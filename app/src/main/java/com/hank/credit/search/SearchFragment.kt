package com.hank.credit.search

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hank.credit.R
import com.hank.credit.data.CardDatabase
import com.hank.credit.databinding.FragmentSearchBinding

class SearchFragment : Fragment(){

    lateinit var viewModel : SearchViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding
        val binding : FragmentSearchBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search,container,false
        )

        val application = requireNotNull(this.activity).application

        //database
        val cardDao = CardDatabase.getInstance(application).cardDao

        //ViewModel
        val viewModelFactory = SearchViewModelFactory(cardDao)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SearchViewModel::class.java)

        //Setting cardList
        val manager = GridLayoutManager(activity,2)
        binding.cardList.layoutManager = manager
        val adapter = SearchAdapter(SearchListener {cardName ->
            binding.root.findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToSearchDetailFragment(cardName))
        })
        binding.cardList.adapter = adapter

        //Setting tagList
        val tagManager = LinearLayoutManager(activity)
        tagManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.searchTagList.layoutManager = tagManager
        val tagAdapter = SearchTagAdapter(viewModel)
        binding.searchTagList.adapter = tagAdapter

        //Live Data
        viewModel.cards.observe(this.viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.tag.observe(this.viewLifecycleOwner, Observer {
            tagAdapter.submitList(it)
        })

        return binding.root
    }
}


