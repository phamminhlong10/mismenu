package com.android.mismenu.features.presentaion.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.android.mismenu.R
import com.android.mismenu.core.adapter.CategoryAdapter
import com.android.mismenu.core.adapter.ProductAdapter
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
        val menuHost: MenuHost = requireActivity()
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        val adapterCategory = CategoryAdapter()
        val adapterNewArrivalProducts = ProductAdapter()
        viewModel.listCategory.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapterCategory.submitList(viewModel.listCategory.value)
                binding.circularCategory.visibility = View.GONE
                binding.adapterCategory.visibility = View.VISIBLE
            }
        })

        viewModel.listArrivalProduct.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapterNewArrivalProducts.submitList(viewModel.listArrivalProduct.value)
                binding.circularProduct.visibility = View.GONE
                binding.adapterProduct.visibility = View.VISIBLE
            }
        })
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapterCategory.adapter = adapterCategory
        binding.adapterProduct.adapter = adapterNewArrivalProducts

        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.appbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner)
        return binding.root
    }

}