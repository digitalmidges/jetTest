package com.digitalmidges.jettest.ui.viewHolders

import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.digitalmidges.jettest.databinding.RowDataErrorBinding

class ErrorDataViewHolder(private val binding: RowDataErrorBinding) : RecyclerView.ViewHolder(binding.root) {



    fun bind(message:String?) {


        if (!TextUtils.isEmpty(message)){
            binding.tvNoData.text = message
        }

    }



}