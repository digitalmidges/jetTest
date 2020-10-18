package com.digitalmidges.jettest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalmidges.jettest.R
import com.digitalmidges.jettest.databinding.RowTagBinding
import com.digitalmidges.jettest.utils.FilterHelper

class FilterAdapter(private val filterItems: ArrayList<FilterHelper.FilterItem>,val callback:IAdapterCallback): RecyclerView.Adapter<FilterAdapter.TagsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.TagsViewHolder {
        val binding = RowTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        if (filterItems.isNullOrEmpty()){
            return 0
        }
        return filterItems.size
    }

    inner class TagsViewHolder constructor(val binding: RowTagBinding) : RecyclerView.ViewHolder(binding.root) {


        init {
            itemView.setOnClickListener {

                callback.onItemClick(filterItems[adapterPosition])
            }
        }


        fun onBind(position: Int) {

            if (filterItems.isNullOrEmpty()){
                return
            }

            val item = filterItems[position]

            if (item.isSelected){
                binding.tvFilter.setBackgroundResource(R.drawable.shape_tag_click)
            }else{
                binding.tvFilter.setBackgroundResource(R.drawable.shape_tag_idle)
            }
            binding.tvFilter.text = item.name
        }
    }

    interface IAdapterCallback{

        fun onItemClick(item:FilterHelper.FilterItem)

    }

}