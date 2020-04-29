package com.hank.credit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hank.credit.databinding.FragmentOtherBinding

class OtherFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentOtherBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_other,container,false
        )
        return binding.root
    }
}