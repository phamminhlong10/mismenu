package com.android.mismenu.features.presentaion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.android.mismenu.features.domain.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val arguments: SavedStateHandle): ViewModel() {
    private val _product = MutableLiveData<Product?>()
    val product: LiveData<Product?>
    get() = _product

    init {
        handleArgument()
    }

    private fun handleArgument(){
        val product = arguments.get<Product>("Product")
        _product.value = product
    }
}