package com.digitalmidges.jettest.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.digitalmidges.jettest.dialogs.DialogLoading

abstract class BaseFragment : Fragment() {


    private lateinit var  dialogLoading: DialogLoading


    protected abstract fun getBundle(bundle: Bundle?)

    protected abstract fun initFragment(v: View)

    protected abstract fun setDefaultViewsBehavior()

    protected abstract fun onFragmentInitializeReady()

    protected abstract fun setViewsClickListeners()

    protected abstract fun subscribeToViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialogLoading = DialogLoading(requireActivity())
        getBundle(this.arguments) // abstract - use in the child
        initFragment(view) // abstract - use in the child
        setDefaultViewsBehavior() // abstract - use in the child
        onFragmentInitializeReady() // abstract - use in the child
        setViewsClickListeners() // abstract - use in the child
        subscribeToViewModel() // abstract - use in the child
    }


    protected  fun isValidString(input: String?): Boolean {
        return !TextUtils.isEmpty(input)
    }

    protected  fun toast(input: String?) {
        if (isValidString(input)) {
            Toast.makeText(activity, input, Toast.LENGTH_LONG).show()
        }
    }

    protected  fun nullCheck(input: String?): String {
        return input ?: ""
    }


    protected  fun hideKeyboard() {
        if (activity != null) {
            val focus = requireActivity().currentFocus
            if (focus != null) {
                val keyboard = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(focus.windowToken, 0)
            }
        }
    }




    protected open fun blockUI() {
        dialogLoading.show()
    }


    protected open fun showUI() {
        dialogLoading.dismiss()
    }



    override fun onPause() {
        super.onPause()
        showUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        showUI()

    }

}