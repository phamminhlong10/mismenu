package com.android.mismenu.features.presentaion.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.android.mismenu.R
import com.android.mismenu.databinding.FragmentOrderBinding
import com.android.mismenu.features.presentaion.BaseFragment
import com.android.mismenu.features.presentaion.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderFragment : BaseFragment() {
    private lateinit var binding: FragmentOrderBinding
    private val viewModel: OrderViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        binding.sentOrder.setOnClickListener {
            popUpAlertDialog(
                this@OrderFragment.requireContext(),
                "Confirm",
                "Your order will be sent to us"
            ).setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }.setPositiveButton("Confirm") { dialog, which ->
                viewModel.orderItems.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        viewModel.onSentOrder()
                        this.findNavController()
                            .navigate(OrderFragmentDirections.actionOrderFragmentToHomeFragment())
                    }
                })
            }.show()
        }

        viewModel.order.observe(viewLifecycleOwner, Observer {

            binding.layoutFieldPhone.error = viewModel.validatePhone()
            binding.layoutFieldAddress.error = viewModel.validateAddress()
            if(viewModel.validatePhone() == null && viewModel.validateAddress() == null && !viewModel.order.value?.address.isNullOrEmpty() && !viewModel.order.value?.phone.isNullOrEmpty()){
                binding.sentOrder.visibility = View.VISIBLE
            }else{
                binding.sentOrder.visibility = View.INVISIBLE
            }
        })

        return binding.root
    }
}