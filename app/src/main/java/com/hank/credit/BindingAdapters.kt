package com.hank.credit

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hank.credit.other.OtherAdapter
import com.hank.credit.other.net.MarsProperty


@BindingAdapter("listData")
fun bindRecycler(recyclerView: RecyclerView, data: List<MarsProperty>?){
    val adapter = recyclerView.adapter as OtherAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
//            .apply(RequestOptions()
//                .placeholder(R.drawable.img_yushan)
//                .error(R.drawable.img_hachi))
            .into(imgView)
    }
}