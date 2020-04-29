package com.hank.credit.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hank.credit.databinding.ListSearchTagBinding

class SearchTagAdapter(val viewModel: SearchViewModel) : ListAdapter<String,SearchTagAdapter.tagViewHolder>(TagDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tagViewHolder {
        return tagViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: tagViewHolder, position: Int) {
        holder.bind(getItem(position),viewModel)
    }

    class tagViewHolder(val binding: ListSearchTagBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: String, viewModel: SearchViewModel){
            binding.name = item
            binding.tagName.setOnCheckedChangeListener(TagListener(item,viewModel))
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): tagViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListSearchTagBinding.inflate(layoutInflater,parent,false)
                return tagViewHolder(binding)
            }
        }
    }
}

class TagDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
//class SearchTagListener(val clickListener: (type: String) -> Unit) {
//    fun onClick(type: String) = clickListener(type)
//}

class TagListener(val type: String, val viewModel : SearchViewModel): CompoundButton.OnCheckedChangeListener{
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if(isChecked){
            viewModel.onTagClicked(type)
        }else{
            viewModel.initializeCard()
        }
    }
}