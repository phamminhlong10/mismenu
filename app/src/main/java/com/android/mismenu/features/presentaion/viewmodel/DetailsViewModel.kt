package com.android.mismenu.features.presentaion.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.android.mismenu.features.domain.data.entities.asCartEntity
import com.android.mismenu.features.domain.entities.Product
import com.android.mismenu.features.domain.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val arguments: SavedStateHandle, private val localRepository: LocalRepository): ViewModel() {
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

    fun onAddToCart(){
        viewModelScope.launch{
            try {
                product.value?.let {
                    addToCart(it)
                }
                Log.d("ADD TO CART", "ADD SUCCESSFULLY")
            }catch (e: Exception){
                Log.d("ADD TO CART", "FAIL: $e")
            }
        }
    }

    private suspend fun addToCart(product: Product){
        return withContext(Dispatchers.IO){
            val entity = product.asCartEntity()
            localRepository.addItemToCart(entity)
        }
    }
}