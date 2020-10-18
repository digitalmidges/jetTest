package com.digitalmidges.jettest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.digitalmidges.jettest.R
import com.digitalmidges.jettest.databinding.FragmentFilterSeekbarBinding
import com.digitalmidges.jettest.databinding.FragmentFilterTagsBinding

class FilterSeekBarFragment:BaseFragment() {

    private var _binding: FragmentFilterSeekbarBinding? = null
    private val binding get() = _binding!!


    companion object {
        fun newInstance() = FilterSeekBarFragment().apply {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFilterSeekbarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getBundle(bundle: Bundle?) {
    }

    override fun initFragment(v: View) {
    }

    override fun setDefaultViewsBehavior() {
    }

    override fun onFragmentInitializeReady() {
        binding.mainLayout.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.clear_blue))
    }

    override fun setViewsClickListeners() {
    }

    override fun subscribeToViewModel() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}