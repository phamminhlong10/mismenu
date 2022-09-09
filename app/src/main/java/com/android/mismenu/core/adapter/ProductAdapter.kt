package com.android.mismenu.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.mismenu.core.util.onClickListener
import com.android.mismenu.databinding.ItemProductBinding
import com.android.mismenu.features.domain.entities.Product

class ProductAdapter(private val onClickListener: onClickListener<Product>) : ListAdapter<Product, ProductAdapter.ViewHolder>(DiffCallBackProduct()) {
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
            onClickListener.clickListener(item)
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