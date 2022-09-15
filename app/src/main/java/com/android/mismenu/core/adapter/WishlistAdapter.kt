package com.android.mismenu.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.mismenu.R
import com.android.mismenu.core.util.ClickListener
import com.android.mismenu.databinding.ItemWishlistBinding
import com.android.mismenu.features.domain.data.entities.WishlistEntity

class WishlistAdapter(private val listener: ClickListener) : ListAdapter<WishlistEntity, WishlistAdapter.ViewHolder>(DiffCallBackWishList()) {
    class ViewHolder(val binding: ItemWishlistBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WishlistEntity){
            binding.wishlistEntity = item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemWishlistBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallBackWishList(): DiffUtil.ItemCallback<WishlistEntity>() {
        override fun areItemsTheSame(oldItem: WishlistEntity, newItem: WishlistEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WishlistEntity, newItem: WishlistEntity): Boolean {
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

        holder.itemView.findViewById<Button>(R.id.removeItemWishlist).setOnClickListener {
            listener.onClickViewInsideViewHolder(item)
        }
    }
}