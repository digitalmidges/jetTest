package com.digitalmidges.jettest.ui.viewHolders

import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.digitalmidges.jettest.databinding.RowNoDataLottieBinding

class NoDataViewHolder(private val binding: RowNoDataLottieBinding) : RecyclerView.ViewHolder(binding.root) {



    fun bind(message:String?) {

        if (!TextUtils.isEmpty(message)){
            binding.tvNoData.text = message
        }

    }



}