package com.android.mismenu.features.presentaion.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.android.mismenu.features.domain.data.entities.CartEntity
import com.android.mismenu.features.domain.entities.Order
import com.android.mismenu.features.domain.repository.LocalRepository
import com.android.mismenu.features.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val localRepository: LocalRepository, private val arguments: SavedStateHandle, private val orderRepository: OrderRepository) : ViewModel() {
    private val _orderItems = localRepository.getAllCartItems()
    val orderItems: LiveData<List<CartEntity>>
    get() = _orderItems

    private val _summary = MutableLiveData<Int>()
    val summary: LiveData<Int>
    get() = _summary

    init {
        handleArguments()
    }

    val name = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val phone = MutableLiveData<String>()

    private fun handleArguments(){
        val summaryPrice = arguments.get<Int>("Summary")
        _summary.value = summaryPrice!!
    }

    private suspend fun sentOrder(order: Order){
        withContext(Dispatchers.IO){
            orderRepository.sentOrder(order)
            localRepository.clearCart()
        }
    }

    fun onSentOrder(){
        viewModelScope.launch() {
            try {
                val order = Order(null, name.value, address.value,  phone.value, null, _orderItems.value, _summary.value, null)
                sentOrder(order)
            }catch (e: Exception){
                Log.d("Cant added", "FAIL: $e")
            }
        }
    }
}