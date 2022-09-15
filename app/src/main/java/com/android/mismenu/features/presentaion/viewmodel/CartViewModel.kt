package com.android.mismenu.features.presentaion.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mismenu.features.domain.data.entities.CartEntity
import com.android.mismenu.features.domain.data.entities.WishlistEntity
import com.android.mismenu.features.domain.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val localRepository: LocalRepository) :ViewModel() {
    private val _cart = localRepository.getAllCartItems()
    val cart: LiveData<List<CartEntity>>
        get() = _cart


    private val _summaryPrice = MutableLiveData<Int?>()
    val summaryPrice: LiveData<Int?>
    get() = _summaryPrice
    private val _item = MutableLiveData<CartEntity?>()
    val item: LiveData<CartEntity?>
        get() = _item

    private val _removeItem = MutableLiveData<CartEntity?>()
    val removeItem: LiveData<CartEntity?>
        get() = _removeItem

    fun itemSelected(cartEntity: CartEntity) {
        _item.value = cartEntity
    }

    fun navigateToDetailsComplete(){
        _item.value = null
    }

    fun removeItemSelected(cartEntity: CartEntity){
        _removeItem.value = cartEntity
    }

    private suspend fun removeItemInCart(item: CartEntity){
        withContext(Dispatchers.IO){
            localRepository.removeItemFromCart(item)
        }
    }


    fun onRemoveItemInCart(){
        viewModelScope.launch{
            try {
                _removeItem.value?.let {
                        item -> removeItemInCart(item)
                    _removeItem.value = null
                }
            }catch (e: Exception){
                Log.d("Cant removed", "FAIL: $e")
            }
        }
    }

    fun calculateSummary(){
        var summary: Int = 0
        _cart.value?.forEach{
                item -> summary += item.price
        }
        _summaryPrice.value = summary
    }
}