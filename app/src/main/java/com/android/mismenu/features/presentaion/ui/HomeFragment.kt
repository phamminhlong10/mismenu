package com.android.mismenu.features.presentaion.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.android.mismenu.R
import com.android.mismenu.core.adapter.CategoryAdapter
import com.android.mismenu.databinding.FragmentHomeBinding
import com.android.mismenu.features.presentaion.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val layoutManager = GridLayoutManager(context, 2)
        val adapter = CategoryAdapter()
        viewModel.listCategory.observe(viewLifecycleOwner, Observer {
            it?.let { adapter.submitList(viewModel.listCategory.value) }
        })

        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapterCategory.adapter = adapter
        return binding.root
    }
}