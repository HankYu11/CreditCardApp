package com.hank.credit.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hank.credit.R
import com.hank.credit.databinding.FragmentOtherBinding

class OtherFragment : Fragment(){

    val viewModel : OtherViewModel by lazy {
        ViewModelProvider(this).get(OtherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOtherBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        binding.otherRecycler.adapter = OtherAdapter()

        setHasOptionsMenu(true)
        return binding.root
    }
}