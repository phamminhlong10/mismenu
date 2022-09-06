package com.android.mismenu.features.presentaion.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mismenu.features.domain.entities.Category
import com.android.mismenu.features.domain.entities.Image
import com.android.mismenu.features.domain.entities.Product
import com.android.mismenu.features.domain.repository.FirestoreManagement
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val fireStoreManagement: FirestoreManagement, private val fireStore: FirebaseFirestore):ViewModel() {
    private val _listCategories = MutableLiveData<List<Category>>()
    val listCategory: LiveData<List<Category>>
        get() = _listCategories

    private val _listNewArrivalProducts = MutableLiveData<List<Product>>()
    val listArrivalProduct: LiveData<List<Product>>
    get() = _listNewArrivalProducts
    init {
        startGetCategories()
    }

    private fun startGetCategories(){
        viewModelScope.launch {
            fetchCategories()
            //demoAddProduct()
            fetchNewArrivalProducts()
        }
    }
    private suspend fun fetchCategories(){
        var list = mutableListOf<Category>()
        return withContext(Dispatchers.IO){
            fireStore.collection("category").get().addOnSuccessListener {collectionsSnapshot  ->
                for (item in collectionsSnapshot){
                    list.add(item.toObject<Category>())
                }
                _listCategories.value = list
            }
        }
    }

    private suspend fun fetchNewArrivalProducts(){
        var listProduct = mutableListOf<Product>()
        return withContext(Dispatchers.IO){
            fireStore.collection("product").get().addOnSuccessListener {collectionsSnapshot  ->
                for (item in collectionsSnapshot){
                    listProduct.add(item.toObject<Product>())
                }
                _listNewArrivalProducts.value = listProduct
            }
        }
    }

    private suspend fun demoAddProduct(){
        return withContext(Dispatchers.IO){
            val list: List<Image> = mutableListOf(
                Image("1", "https://cdn.shopify.com/s/files/1/0177/2424/products/IMG_2306_1024x1024.jpg?v=1661464822"),
                Image("2", "https://cdn.shopify.com/s/files/1/0177/2424/products/IMG_2310_1024x1024.jpg?v=1661464837"),
                Image("3", "https://cdn.shopify.com/s/files/1/0177/2424/products/IMG_2307_1024x1024.jpg?v=1661464837"),
                Image("4", "https://cdn.shopify.com/s/files/1/0177/2424/products/URSPECIALHOVERS_0006_027A0229copy_1024x1024.jpg?v=1662070424")

            )
            val cate = mutableListOf<String>()
            val doc = fireStore.collection("category").get().addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach {
                    Log.d("DOCUMENT ID", it.id)
                    cate.add(it.id)
                }
            }
            Log.d("ID FROM CATE", "$cate")

//            val product = Product("421416", "Buddies In Space Jacket (Black Denim Wash)", "", 3333400, false, list,doc)
//            fireStore.collection("product").add(product)
        }
    }
}