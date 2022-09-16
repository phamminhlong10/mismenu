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


    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order>
        get() = _order

    init {
        _order.postValue(Order(summary = argumentsSummary()))
    }


    private fun argumentsSummary(): Int? {
        return arguments.get<Int>("Summary")
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
                val order = Order(name = _order.value?.name, address = _order.value?.address,  phone = _order.value?.phone,  ItemOrder = _orderItems.value, summary = _order.value?.summary)
                sentOrder(order)
            }catch (e: Exception){
                Log.d("Cant added", "FAIL: $e")
            }
        }
    }
}