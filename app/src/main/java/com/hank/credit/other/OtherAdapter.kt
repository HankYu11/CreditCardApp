package com.hank.credit.other

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hank.credit.databinding.ListOtherBinding
import com.hank.credit.other.net.MarsProperty

class OtherAdapter : ListAdapter<MarsProperty,OtherAdapter.OtherViewHolder>(OtherDiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherViewHolder {
        return OtherViewHolder(ListOtherBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: OtherViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.bind(marsProperty)
    }

    companion object OtherDiffCallback : DiffUtil.ItemCallback<MarsProperty>(){
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class OtherViewHolder(val binding:ListOtherBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(marsProperty: MarsProperty){
            binding.property = marsProperty
            binding.executePendingBindings()
        }
    }
}