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
import com.android.mismenu.databinding.FragmentLoginBinding
import com.android.mismenu.features.presentaion.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login, container, false)
        binding.viewModel = viewModel

        //authentication.signInWithEmailPassword("authentication12@gmail.com", "123456")
        viewModel.user.observe(viewLifecycleOwner, Observer {
                user -> user?.let {
            this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }})

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}