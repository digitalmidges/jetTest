package com.digitalmidges.jettest.dialogs

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.digitalmidges.jettest.R


class LoadingDialog : BaseDialogFragment() {

    companion object {
        fun newInstance() = LoadingDialog().apply {}
    }

    override fun canBeExitByUser(): Boolean {
        return false
    }

    override fun setLayout(): Int {
        return R.layout.dialog_loading_lottie
    }

    override fun beforeInit() {
    }

    override fun init(v: View?) {
    }

    override fun afterInit() {
    }

    override fun onBackPressListener() {
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        isCancelable = true
        if (dialog.window != null && context != null) {
            dialog.window!!.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        requireContext(), R.color.black_overlay
                    )
                )
            )
        }
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null && dialog.window != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }


}