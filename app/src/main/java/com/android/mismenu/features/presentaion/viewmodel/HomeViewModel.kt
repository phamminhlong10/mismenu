package com.android.mismenu.features.presentaion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mismenu.core.util.Utils
import com.android.mismenu.features.domain.entities.Category
import com.android.mismenu.features.domain.entities.Product
import com.android.mismenu.features.domain.repository.Authentication
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val authentication: Authentication, private val fireStore: FirebaseFirestore, private val util: Utils):ViewModel() {
    private val _welcomeTitle = MutableLiveData<String>()
    val welcomeTitle: LiveData<String>
    get() = _welcomeTitle

    private val _listCategories = MutableLiveData<List<Category>>()
    val listCategory: LiveData<List<Category>>
        get() = _listCategories

    private val _listNewArrivalProducts = MutableLiveData<List<Product>>()
    val listArrivalProduct: LiveData<List<Product>>
    get() = _listNewArrivalProducts

    private val _product = MutableLiveData<Product?>()
    val product: LiveData<Product?>
    get() = _product

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?>
        get() = _user

    init {
        startGetCategories()
        setWelcomeTitle()
        userLogin()
    }

    private fun startGetCategories(){
        viewModelScope.launch {
            fetchCategories()
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
            fireStore.collection("product").orderBy("timeAdded", Query.Direction.DESCENDING).get().addOnSuccessListener { collectionsSnapshot  ->
                for (item in collectionsSnapshot){
                    listProduct.add(item.toObject<Product>())
                }
                _listNewArrivalProducts.value = listProduct
            }
        }
    }

    private fun setWelcomeTitle(){
        _welcomeTitle.value = util.welcomeText()
    }

    fun productSelected(product: Product){
        _product.value = product
    }

    fun navigatedToDetailProductComplete(){
        _product.value = null
    }

    private fun userLogin(){
        _user.value = authentication.currentUser()
    }
}