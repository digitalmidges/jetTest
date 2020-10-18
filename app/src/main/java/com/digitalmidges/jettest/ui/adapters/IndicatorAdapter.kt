package com.digitalmidges.jettest.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Space
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.digitalmidges.jettest.R

class IndicatorAdapter(val context: Context?, private var totalCount: Int?) : RecyclerView
.Adapter<IndicatorAdapter.ViewHolder>() {

    private var selectedPosition:Int = 0


    private var indicatorOn:Int = R.drawable.indicator_on_oval_shape
    private var indicatorOff:Int = R.drawable.indicator_off_oval_shape

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_pager_indicator, parent, false))
    }


    fun setIndicatorImages(@DrawableRes indicatorOn:Int,@DrawableRes indicatorOff:Int){
        this.indicatorOn = indicatorOn
        this.indicatorOff = indicatorOff
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (selectedPosition==position){
            holder.imgCircleIndicator.setImageResource(indicatorOn)
        }else{
            holder.imgCircleIndicator.setImageResource(indicatorOff)
        }

        if (totalCount!=null) {
            if (position == totalCount!! - 1) {
                // last item
                holder.space.visibility = View.GONE
            }else{
                holder.space.visibility = View.VISIBLE
            }
        }


    }

    override fun getItemCount(): Int {
        if (totalCount != null) {
            return totalCount as Int
        }
        return 0
//        return 100
    }

    fun updateData(selectedPosition: Int) {
        this.selectedPosition = selectedPosition
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCircleIndicator: ImageView = view.findViewById(R.id.imgIndicator)
        val space: Space = view.findViewById(R.id.space)

    }
}