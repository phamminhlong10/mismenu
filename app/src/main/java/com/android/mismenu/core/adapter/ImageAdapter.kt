package com.android.mismenu.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.mismenu.databinding.ItemImageBinding
import com.android.mismenu.features.domain.entities.Image

class ImageAdapter(): ListAdapter<Image, ImageAdapter.ViewHolder>(DiffCallBackImage()) {
    class DiffCallBackImage: DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.urlImage == oldItem.urlImage
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.urlImage == oldItem.urlImage
        }

    }

    class ViewHolder(val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image){
            binding.image = image
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemImageBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}