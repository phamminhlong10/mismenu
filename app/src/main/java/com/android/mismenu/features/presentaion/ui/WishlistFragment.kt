package com.android.mismenu.features.presentaion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.android.mismenu.R
import com.android.mismenu.core.adapter.WishlistAdapter
import com.android.mismenu.core.util.ClickListenerWishlistImp
import com.android.mismenu.databinding.FragmentWishlistBinding
import com.android.mismenu.features.presentaion.BaseFragment
import com.android.mismenu.features.presentaion.viewmodel.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : BaseFragment() {
    private lateinit var binding: FragmentWishlistBinding
    private val viewModel: WishlistViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wishlist, container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        setTitleAppBar("Your wishlist")
        val wishListAdapter = WishlistAdapter(ClickListenerWishlistImp(viewModel))

        viewModel.wishlist.observe(viewLifecycleOwner, Observer {
            it?.let {
                wishListAdapter.submitList(it)
            }
        })

        viewModel.removeItem.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.onRemoveItem()
            }
        })

        viewModel.item.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(WishlistFragmentDirections.actionWishlistFragmentToDetailsFragment(null, it, null))
                viewModel.navigatedToDetailComplete()
            }
        })

        binding.wishList.adapter = wishListAdapter
        return binding.root
    }
}
