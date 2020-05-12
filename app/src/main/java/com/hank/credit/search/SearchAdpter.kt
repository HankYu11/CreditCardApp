package com.hank.credit.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hank.credit.data.Card
import com.hank.credit.databinding.ListSearchCardBinding

class SearchAdapter(val clickListener: SearchListener) : ListAdapter<Card,SearchAdapter.ViewHolder>(CardDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = getItem(position)
        holder.bind(card,clickListener)
    }

    class ViewHolder(val binding : ListSearchCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: Card,
            clickListener: SearchListener
        ){
            binding.card = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListSearchCardBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }

    }
}

class CardDiffCallback : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return  oldItem.cardName == newItem.cardName
    }
}

class SearchListener(val clickListener: (cardName: String) -> Unit) {
    fun onClick(cardName: String) = clickListener(cardName)
}


