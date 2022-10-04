package com.android.mismenu.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.mismenu.R
import com.android.mismenu.core.util.ClickListener
import com.android.mismenu.databinding.ItemFilterBinding
import com.android.mismenu.databinding.ItemProductBinding
import com.android.mismenu.features.domain.entities.Product
import com.android.mismenu.features.presentaion.viewmodel.HomeViewModel
import javax.inject.Inject
import javax.inject.Singleton

class FilterAdapter (private val listener: ClickListener) : ListAdapter<Product, FilterAdapter.ViewHolder>(DiffCallBackProduct()), Filterable {
    private var list = listOf<Product>()
    class ViewHolder(private val binding: ItemFilterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.product = product
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFilterBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    fun setData(list: List<Product>){
        this.list = list
        submitList(list)
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
    }

    class DiffCallBackProduct : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id ==  newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id ==  newItem.id
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(query: CharSequence?): FilterResults? {
                val filteredList = mutableListOf<Product>()
                val input = query.toString()
                if(input.isNullOrEmpty()){
                    filteredList.addAll(list)
                }else{
                    for (item in list) {
                        if (item.name?.lowercase()!!.contains(input.lowercase())) {
                            filteredList.add(item)
                        }
                    }
                }
                val result = Filter.FilterResults()
                result?.values = filteredList
                return result
            }

            override fun publishResults(query: CharSequence?, filterResults: FilterResults?) {
                submitList(filterResults?.values as List<Product>?)
            }
        }
    }
}