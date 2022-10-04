package com.android.mismenu.features.presentaion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mismenu.features.domain.entities.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val fireStore: FirebaseFirestore): ViewModel() {
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
            fetchNewArrivalProducts()
        }
    }

    fun itemSelected(product: Product){
        _item.value = product
    }

    fun navigateToDetailsComplete(){
        _item.value = null
    }

    private suspend fun fetchNewArrivalProducts(){
        var listProduct = mutableListOf<Product>()
        return withContext(Dispatchers.IO){
            fireStore.collection("product").orderBy("timeAdded", Query.Direction.DESCENDING).get().addOnSuccessListener { collectionsSnapshot  ->
                for (item in collectionsSnapshot){
                    listProduct.add(item.toObject<Product>())
                }
                _listFilter.value = listProduct
            }
        }
    }
}