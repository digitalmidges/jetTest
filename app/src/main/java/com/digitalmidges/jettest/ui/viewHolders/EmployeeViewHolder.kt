package com.digitalmidges.jettest.ui.viewHolders

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digitalmidges.jettest.R
import com.digitalmidges.jettest.databinding.RowEmployeeBinding
import com.digitalmidges.jettest.network.pojos.responses.EmployeeResponse
import com.digitalmidges.jettest.ui.adapters.HomeAdapter
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt


class EmployeeViewHolder(private val binding: RowEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {


    //    private val tvNoData: TextView = view.findViewById(R.id.tvNoData)
    private val TAG = "EmployeeViewHolder"

    fun bind(employeeItem: EmployeeResponse?, position: Int, expandCellHeight: Int, expandCellMargin: Int, defaultMargin: Int, callback: HomeAdapter.IHomePageAdapterCallback?) {

        if (employeeItem == null) {
            return
        }





        binding.apply {


            tvEmployeeName.text = employeeItem.employee_name ?: "John Do"

            // default image
            binding.imgProfile.setImageResource(R.drawable.profile_image_silhouette_2)


            if (employeeItem.employee_age.isNullOrEmpty()) {
                tvEmployeeAge.visibility = View.GONE
            } else {
                tvEmployeeAge.visibility = View.VISIBLE
                tvEmployeeAge.text = itemView.context.getString(R.string.employee_age_place_holder, employeeItem.employee_age)
            }


            if (employeeItem.employee_salary.isNullOrEmpty()) {
                tvEmployeeSalary.visibility = View.GONE
            } else {
                tvEmployeeSalary.visibility = View.VISIBLE

                val df = DecimalFormat("#,###.##")
                df.roundingMode = RoundingMode.FLOOR
                val formatSalary = df.format(employeeItem.employee_salary!!.toInt())

                tvEmployeeSalary.text = itemView.context.getString(R.string.employee_salary_place_holder, formatSalary)
            }



            if (employeeItem.profile_image.isNullOrEmpty()) {
                binding.imgProfile.setImageResource(R.drawable.profile_image_silhouette_2)
            } else {
                Glide.with(binding.root)
                    .load(employeeItem.profile_image)
                    .centerCrop()
                    .placeholder(R.drawable.place_holder_shape)
                    .error(R.drawable.profile_image_silhouette_2)
                    .into(binding.imgProfile)
            }



            itemView.setOnClickListener {
                callback?.onRowClick(employeeItem, position)


            }


            val isExpandable = employeeItem.isExpandable
            val isRowClicked = employeeItem.isRowClicked
            val wasExpandable = employeeItem.wasExpandable

            val marginGap = defaultMargin - expandCellMargin // for animation - the gap between full collapse side margin to full expand

            var animationDuration = 0
            if (isRowClicked) {
                animationDuration = 200
            }

            if (isExpandable) {


                // EXPAND ANIMATION!!


                imgArrow.animate()
                    .setDuration(animationDuration.toLong())
                    .rotation(90F)


                if (isRowClicked) {


                    frameExpanded.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    val realImageHeight: Int = imgProfile.measuredHeight
                    val actualHeight: Int = expandCellHeight

                    frameExpanded.layoutParams.height = 0
                    frameExpanded.visibility = View.VISIBLE
                    frameExpanded.requestLayout()

                    val animation: Animation = object : Animation() {
                        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {

                            var cellHeight = 0
                            var margin = 0
                            if (interpolatedTime == 1f) {
                                //                                cellHeight=   ViewGroup.LayoutParams.WRAP_CONTENT
                                cellHeight = expandCellHeight
                                margin = expandCellMargin
                            } else {
                                cellHeight = (actualHeight * interpolatedTime).toInt()
                                margin = defaultMargin - (marginGap * interpolatedTime).roundToInt()
                            }

                            frameExpanded.layoutParams.height = cellHeight
                            frameExpanded.requestLayout()

                            imgProfile.layoutParams.height = cellHeight
                            imgProfile.requestLayout()

                            val newLayoutParams = mainContent.layoutParams as RecyclerView.LayoutParams
                            newLayoutParams.topMargin = 0
                            newLayoutParams.bottomMargin = defaultMargin
                            newLayoutParams.leftMargin = margin
                            newLayoutParams.rightMargin = margin
                            mainContent.layoutParams = newLayoutParams

                        }
                    }

                    animation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {

                        }

                        override fun onAnimationEnd(p0: Animation?) {

                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                    })



                    animation.duration = (actualHeight / frameExpanded.context.resources.displayMetrics.density).toLong()

                    frameExpanded.startAnimation(animation)
                } else {

                    val newLayoutParams = mainContent.layoutParams as RecyclerView.LayoutParams
                    newLayoutParams.topMargin = 0
                    newLayoutParams.bottomMargin = expandCellMargin
                    newLayoutParams.leftMargin = expandCellMargin
                    newLayoutParams.rightMargin = expandCellMargin
                    mainContent.layoutParams = newLayoutParams

                    frameExpanded.visibility = View.VISIBLE
                }


            } else {


                // COLLAPSE ANIMATION

                imgArrow.animate()
                    .setDuration(animationDuration.toLong())
                    .rotation(0F)



                if (isRowClicked || wasExpandable) {
                    val actualHeight: Int = imgProfile.measuredHeight

                    val animation: Animation = object : Animation() {
                        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {

                            var margin = 0

                            if (interpolatedTime == 1f) {
                                frameExpanded.visibility = View.GONE
                                margin = defaultMargin
                            } else {

                                margin = (marginGap * interpolatedTime).roundToInt() + expandCellMargin

                                val cellHeight = actualHeight - (actualHeight * interpolatedTime).toInt()

                                frameExpanded.layoutParams.height = cellHeight
                                frameExpanded.requestLayout()

                                imgProfile.layoutParams.height = cellHeight
                                imgProfile.requestLayout()
                            }

                            val newLayoutParams = mainContent.layoutParams as RecyclerView.LayoutParams
                            newLayoutParams.topMargin = 0
                            newLayoutParams.bottomMargin = defaultMargin
                            newLayoutParams.leftMargin = margin
                            newLayoutParams.rightMargin = margin
                            mainContent.layoutParams = newLayoutParams

                        }

                    }

                    animation.duration = (actualHeight / frameExpanded.context.resources.displayMetrics.density).toLong()
                    frameExpanded.startAnimation(animation)
                } else {

                    frameExpanded.visibility = View.GONE

                    val newLayoutParams = mainContent.layoutParams as RecyclerView.LayoutParams
                    newLayoutParams.topMargin = 0
                    newLayoutParams.bottomMargin = defaultMargin
                    newLayoutParams.leftMargin = defaultMargin
                    newLayoutParams.rightMargin = defaultMargin
                    mainContent.layoutParams = newLayoutParams
                }

            }


            employeeItem.isRowClicked = false
            employeeItem.wasExpandable = false

        }


    }


}