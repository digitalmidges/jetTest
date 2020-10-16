package com.digitalmidges.jettest.ui.viewHolders

import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.digitalmidges.jettest.databinding.RowDataErrorBinding
import com.digitalmidges.jettest.databinding.RowNoNetworkLottieBinding
import com.digitalmidges.jettest.ui.adapters.HomeAdapter

class ErrorNoNetworkViewHolder(private val binding: RowNoNetworkLottieBinding) : RecyclerView.ViewHolder(binding.root) {



    fun bind(callback: HomeAdapter.IHomePageAdapterCallback?) {


        itemView.setOnClickListener {
            callback?.onNoNetworkButtonClick()

        }


    }



}