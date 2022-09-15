package com.android.mismenu.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.mismenu.R
import com.android.mismenu.core.util.ClickListener
import com.android.mismenu.databinding.ItemCartBinding
import com.android.mismenu.features.domain.data.entities.CartEntity

class CartAdapter(private val listener: ClickListener) : ListAdapter<CartEntity, CartAdapter.ViewHolder>(DiffCallBackCart()) {
    class ViewHolder(val binding: ItemCartBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartEntity){
            binding.cartEntity = item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCartBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallBackCart(): DiffUtil.ItemCallback<CartEntity>() {
        override fun areItemsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            listener.onItemClickListener(item)
        }

        holder.itemView.findViewById<Button>(R.id.removeItemInCart).setOnClickListener {
            listener.onClickViewInsideViewHolder(item)
        }
    }
}