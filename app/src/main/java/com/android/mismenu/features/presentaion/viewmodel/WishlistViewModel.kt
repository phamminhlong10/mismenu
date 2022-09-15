package com.android.mismenu.features.presentaion.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mismenu.features.domain.data.entities.WishlistEntity
import com.android.mismenu.features.domain.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(private val localRepository: LocalRepository) : ViewModel() {
    private val _wishlist = localRepository.getAllWishlistItems()
    val wishlist: LiveData<List<WishlistEntity>>
    get() = _wishlist

    private val _removeItem = MutableLiveData<WishlistEntity?>()
    val removeItem: LiveData<WishlistEntity?>
    get() = _removeItem

    private val _item = MutableLiveData<WishlistEntity?>()
    val item: LiveData<WishlistEntity?>
        get() = _item

    private suspend fun removeItemWishlist(item: WishlistEntity){
        withContext(Dispatchers.IO){
            localRepository.removeItemFromWishList(item)
        }
    }

    fun onRemoveItem(){
        viewModelScope.launch {
            try{
                _removeItem.value?.let { item -> removeItemWishlist(item) }
                _removeItem.value = null
            }catch (e: Exception){
                Log.d("Cant removed", "FAIL: $e")
            }
        }
    }

    fun removeItemSelected(item: WishlistEntity){
        _removeItem.value = item
    }

    fun itemSelected(item: WishlistEntity){
        _item.value = item
    }

    fun navigatedToDetailComplete(){
        _item.value = null
    }
}