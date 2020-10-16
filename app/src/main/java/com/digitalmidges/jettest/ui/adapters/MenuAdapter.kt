package com.digitalmidges.jettest.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalmidges.jettest.R
import com.digitalmidges.jettest.databinding.RowMenuItemBinding
import com.digitalmidges.jettest.ui.activities.MainActivity

class MenuAdapter(val activity: Activity?, private var menuList: ArrayList<MainActivity.MenuObject>, private var callback: IMenuAdapterCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object{
        private const val TYPE_REGULAR_ITEM = R.layout.row_menu_item
    }


    override fun getItemViewType(position: Int): Int {
        return TYPE_REGULAR_ITEM
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binding = RowMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {

            is ItemViewHolder -> {
                val item = menuList[position]
                holder.bind(item)
            }

        }


    }

    override fun getItemCount(): Int {
        return menuList.size
    }


    inner class ItemViewHolder(val binding: RowMenuItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MainActivity.MenuObject) {
            binding.tvMenu.text = item.title
            itemView.setOnClickListener {
                callback.onItemClick(item)
            }

        }

    }



    interface IMenuAdapterCallback{
        fun onItemClick(menuItem: MainActivity.MenuObject)
    }





}