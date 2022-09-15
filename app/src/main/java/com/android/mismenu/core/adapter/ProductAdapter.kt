package com.android.mismenu.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.mismenu.R
import com.android.mismenu.core.util.ClickListener
import com.android.mismenu.databinding.ItemProductBinding
import com.android.mismenu.features.domain.entities.Product
import com.android.mismenu.features.presentaion.viewmodel.HomeViewModel
import javax.inject.Inject
import javax.inject.Singleton

class ProductAdapter (private val listener: ClickListener) : ListAdapter<Product, ProductAdapter.ViewHolder>(DiffCallBackProduct()) {
    class ViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.product = product
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener{
            listener.onItemClickListener(item)
        }

        holder.itemView.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.addToWishlistButton).setOnClickListener{
            listener.onClickViewInsideViewHolder(item)
        }
        
    }

    class DiffCallBackProduct : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id ==  newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id ==  newItem.id
        }
    }


}