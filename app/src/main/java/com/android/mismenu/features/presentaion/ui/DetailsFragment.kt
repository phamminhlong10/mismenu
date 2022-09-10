package com.android.mismenu.features.presentaion.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.android.mismenu.R
import com.android.mismenu.core.adapter.ImageAdapter
import com.android.mismenu.databinding.FragmentDetailsBinding
import com.android.mismenu.features.presentaion.BaseFragment
import com.android.mismenu.features.presentaion.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        turnOffShadowAppBar()
        viewModel.product.observe(viewLifecycleOwner, Observer {
            product -> product?.name?.let { it -> setTitleAppBar(it) }
        })

        val imageAdapter = ImageAdapter()

        viewModel.product.observe(viewLifecycleOwner, Observer{
            product -> imageAdapter.submitList(product?.imageOfProduct)
        })

        viewModel.product.observe(viewLifecycleOwner, Observer {
            product -> if(product?.isSoldOut == true){
                binding.addToCard.visibility = View.GONE
                binding.soldOut.visibility = View.VISIBLE
        }
        })

        binding.pager.adapter = imageAdapter

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            when(group.checkedChipId){
                R.id.smallChip -> Log.d("CHIP SELECTED", "Small")
                R.id.mediumChip -> Log.d("CHIP SELECTED", "Medium")
                R.id.largeChip -> Log.d("CHIP SELECTED", "Large")
                R.id.xLargeChip -> Log.d("CHIP SELECTED", "xLarge")
                else -> Log.d("CHIP SELECTED", "Do not have id equals ${group.checkedChipId}")
            }
        }

        return binding.root
    }
}