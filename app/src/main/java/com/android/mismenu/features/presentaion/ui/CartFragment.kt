package com.android.mismenu.features.presentaion.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.android.mismenu.R
import com.android.mismenu.core.adapter.CartAdapter
import com.android.mismenu.core.util.ClickListener
import com.android.mismenu.core.util.ClickListenerItemInCart
import com.android.mismenu.databinding.FragmentCartBinding
import com.android.mismenu.features.presentaion.BaseFragment
import com.android.mismenu.features.presentaion.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment() {
    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cart, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setTitleAppBar("Shopping cart")
        val cartAdapter = CartAdapter(ClickListenerItemInCart(viewModel))

        viewModel.cart.observe(viewLifecycleOwner, Observer {
            it?.let {
                cartAdapter.submitList(it)
                viewModel.calculateSummary()
            }
        })
        viewModel.item.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(CartFragmentDirections.actionCartFragmentToDetailsFragment(null, null, it))
                viewModel.navigateToDetailsComplete()
            }
        })

        viewModel.removeItem.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.onRemoveItemInCart()
            }
        })

        binding.processToCheckout.setOnClickListener {
            viewModel.summaryPrice.observe(viewLifecycleOwner, Observer {
                it?.let {
                    if(it != 0){
                        this.findNavController().navigate(CartFragmentDirections.actionCartFragmentToOrderFragment(it))
                    }
                }
            })
        }
        binding.cart.adapter = cartAdapter
        return binding.root
    }
}