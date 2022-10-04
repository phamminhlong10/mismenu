package com.android.mismenu.features.presentaion.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.android.mismenu.R
import com.android.mismenu.core.adapter.FilterAdapter
import com.android.mismenu.core.adapter.ProductAdapter
import com.android.mismenu.core.util.ClickListener
import com.android.mismenu.core.util.ClickListenerItemInCart
import com.android.mismenu.core.util.ClickListenerItemInSearch
import com.android.mismenu.core.util.ClickListenerNewArrivalImp
import com.android.mismenu.databinding.FragmentSearchBinding
import com.android.mismenu.features.domain.entities.Product
import com.android.mismenu.features.presentaion.BaseFragment
import com.android.mismenu.features.presentaion.viewmodel.HomeViewModel
import com.android.mismenu.features.presentaion.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setTitleAppBar("Search")

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = FilterAdapter(ClickListenerItemInSearch(viewModel))
        viewModel.listFilter.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setData(it)
            }
        })

        viewModel.item.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(it, null, null))
                viewModel.navigateToDetailsComplete()
            }
        })

        binding.filterProduct.adapter = adapter

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }
        })
        return binding.root
    }
}