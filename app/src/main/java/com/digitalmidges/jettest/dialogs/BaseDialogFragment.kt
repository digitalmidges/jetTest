package com.digitalmidges.jettest.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class BaseDialogFragment : DialogFragment() {

    protected abstract fun canBeExitByUser(): Boolean

    protected abstract fun setLayout(): Int

    protected abstract fun beforeInit()

    protected abstract fun init(v: View?)

    protected abstract fun afterInit()

    protected abstract fun onBackPressListener()

    private var isShowing = false

    private val TAG = "BaseDialogFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val canBeExitByUser = canBeExitByUser()
        isCancelable = canBeExitByUser
        dialog?.setCancelable(canBeExitByUser)
        dialog?.setCanceledOnTouchOutside(canBeExitByUser)
        return inflater.inflate(setLayout(), container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBaseDialogFeatures(view)
        beforeInit() // abstract
        init(view) // abstract
        afterInit() // abstract
    }

    private fun initBaseDialogFeatures(view: View) {


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val canBeExitByUser = canBeExitByUser()
        if (canBeExitByUser) {
            isCancelable = false
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setOnKeyListener { arg0: DialogInterface?, keyCode: Int, event: KeyEvent? ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    onBackPressListener()
                }
                true
            }
        }
        return dialog
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        setShowing(false)
    }

    override fun show(
        manager: FragmentManager,
        tag: String?
    ) {
        if (isShowing) {
            return
        }
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commit()
            isShowing = true
        } catch (e: IllegalStateException) {
            Log.d(TAG, "FragmentTransaction Exception $e")
        }
        //        super.show(manager, tag);
    }


    fun isShowing(): Boolean {
        return isShowing
    }

    private fun setShowing(showing: Boolean) {
        isShowing = showing
    }


}