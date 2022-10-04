package com.android.mismenu.features.presentaion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mismenu.features.domain.entities.Product
import com.android.mismenu.features.domain.repository.ProductRepository
import com.android.mismenu.features.domain.repository.ResultsCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val fireStore: FirebaseFirestore, private val productRepository: ProductRepository): ViewModel() {
    private val _listFilter = MutableLiveData<List<Product>?>()
    val listFilter: LiveData<List<Product>?>
    get() = _listFilter

    private val _item = MutableLiveData<Product?>()
    val item: LiveData<Product?>
    get() = _item

    init {
        loadData()
    }

    private fun loadData(){
        viewModelScope.launch {
            productRepository.getAllProducts(object : ResultsCallback{
                override fun onListProductsCallback(list: List<Product>) {
                    _listFilter.value = list
                }

                override fun onProductCallback(product: Product?) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    fun itemSelected(product: Product){
        _item.value = product
    }

    fun navigateToDetailsComplete(){
        _item.value = null
    }
}