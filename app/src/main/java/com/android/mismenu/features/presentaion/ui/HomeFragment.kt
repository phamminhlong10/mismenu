package com.android.mismenu.features.presentaion.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.android.mismenu.R
import com.android.mismenu.core.adapter.CategoryAdapter
import com.android.mismenu.core.adapter.ProductAdapter
import com.android.mismenu.core.util.ClickListener
import com.android.mismenu.core.util.ClickListenerNewArrivalImp
import com.android.mismenu.databinding.FragmentHomeBinding
import com.android.mismenu.features.domain.entities.Product
import com.android.mismenu.features.presentaion.BaseFragment
import com.android.mismenu.features.presentaion.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showAppbar(true)
        val menuHost: MenuHost = requireActivity()
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        val adapterCategory = CategoryAdapter()
        val adapterNewArrivalProducts = ProductAdapter(ClickListenerNewArrivalImp(viewModel))

        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapterCategory.adapter = adapterCategory
        binding.adapterProduct.adapter = adapterNewArrivalProducts



        //Check login if no sign in -> navigate to login fragments
        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            if (user == null){
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        })

        //Loading & display categories
        viewModel.listCategory.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapterCategory.submitList(it)
                binding.circularCategory.visibility = View.GONE
                binding.adapterCategory.visibility = View.VISIBLE
            }
        })

        //Loading & display new arrival
        viewModel.listArrivalProduct.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapterNewArrivalProducts.submitList(it)
                binding.circularProduct.visibility = View.GONE
                binding.adapterProduct.visibility = View.VISIBLE
            }
        })

        //Title appbar
        viewModel.welcomeTitle.observe(viewLifecycleOwner, Observer {
            setTitleAppBar(it)
        })

        //Navigate to detail item
        viewModel.product.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it, null, null))
                viewModel.navigatedToDetailProductComplete()
            }
        })

        //Add item to wishlist
        viewModel.wishLIstItem.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.onAddToWishlist()
            }
        })

        //Menu in app bar
        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.appbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.wishlist -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWishlistFragment())
                    R.id.cart -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCartFragment())
                }
                return false
            }
        }, viewLifecycleOwner)
        return binding.root
    }

}