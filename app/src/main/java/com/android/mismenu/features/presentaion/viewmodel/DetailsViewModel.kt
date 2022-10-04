package com.android.mismenu.features.presentaion.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.android.mismenu.features.domain.data.entities.CartEntity
import com.android.mismenu.features.domain.data.entities.WishlistEntity
import com.android.mismenu.features.domain.data.entities.asCartEntity
import com.android.mismenu.features.domain.data.entities.asWishlistEntity
import com.android.mismenu.features.domain.entities.Product
import com.android.mismenu.features.domain.repository.LocalRepository
import com.android.mismenu.features.domain.repository.ProductRepository
import com.android.mismenu.features.domain.repository.ResultsCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val arguments: SavedStateHandle,
    private val localRepository: LocalRepository,
    private val productRepository: ProductRepository): ViewModel() {
    private val _product = MutableLiveData<Product?>()
    val product: LiveData<Product?>
        get() = _product

    private val _wishlistEntity = MutableLiveData<WishlistEntity?>()
    val wishlistEntity: LiveData<WishlistEntity?>
        get() = _wishlistEntity

    private val _cartEntity = MutableLiveData<CartEntity?>()
    val cartEntity: LiveData<CartEntity?>
        get() = cartEntity

    private val _size = MutableLiveData<String>()
    val size: LiveData<String>
        get() = _size

    init {
        handleArgument()
        fetchItem()
    }

    private fun handleArgument(){
        val product = arguments.get<Product>("Product")
        val wishlistEntity = arguments.get<WishlistEntity>("WishlistEntity")
        val cartEntity = arguments.get<CartEntity>("CartEntity")
        _product.value = product
        _wishlistEntity.value = wishlistEntity
        _cartEntity.value = cartEntity
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

    fun onAddToWishlist(){
        viewModelScope.launch{
            try {
                product.value?.let {
                    addToWishList(it)
                }
                Log.d("ADD TO Wishlist", "ADD SUCCESSFULLY")
            }catch (e: Exception){
                Log.d("ADD TO Wishlist", "FAIL: $e")
            }
        }
    }

    private suspend fun addToCart(product: Product){
        withContext(Dispatchers.IO){
            val entity = product.asCartEntity()
            entity.size = size.value!!
            localRepository.addItemToCart(entity)
        }
    }

    private suspend fun addToWishList(product: Product){
        withContext(Dispatchers.IO){
            val entity = product.asWishlistEntity()
            localRepository.addItemToWishlist(entity)
        }
    }

    fun setSizeProduct(size: String){
        _size.value = size
    }

    private fun fetchItem(){
        viewModelScope.launch {

            _wishlistEntity.value?.let {
                productRepository.getProductDetail(_wishlistEntity.value!!.id, object : ResultsCallback{
                    override fun onListProductsCallback(list: List<Product>) {
                        TODO("Not yet implemented")
                    }
                    override fun onProductCallback(product: Product?) {
                        _product.value = product
                    }
                })
            }

            _cartEntity.value?.let {
                productRepository.getProductDetail(_cartEntity.value!!.idProduct, object : ResultsCallback{
                    override fun onListProductsCallback(list: List<Product>) {
                        TODO("Not yet implemented")
                    }
                    override fun onProductCallback(product: Product?) {
                        _product.value = product
                    }
                })
            }
        }
    }
}