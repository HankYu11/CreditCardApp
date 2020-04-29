package com.hank.credit.mycards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hank.credit.databinding.ListMycardsCardBinding

class MycardsAdapter() : ListAdapter<CardCoupon, MycardsAdapter.ViewHolder>(MyCardDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardCoupon = getItem(position)
        holder.bind(cardCoupon)
    }

    class ViewHolder(val binding : ListMycardsCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: CardCoupon){
            binding.cardCoupon = item
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListMycardsCardBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }
}

class MyCardDiffCallback : DiffUtil.ItemCallback<CardCoupon>() {
    override fun areItemsTheSame(oldItem: CardCoupon, newItem: CardCoupon): Boolean {
        return  oldItem.cardName == newItem.cardName
    }
    override fun areContentsTheSame(oldItem: CardCoupon, newItem: CardCoupon): Boolean {
        return  oldItem == newItem
    }
}