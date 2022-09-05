package com.android.mismenu.features.presentaion.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mismenu.features.domain.entities.Category
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
    init {
        startGetCategories()
    }

    private fun startGetCategories(){
        viewModelScope.launch {
            fetchCategories()
        }
    }

    suspend fun fetchCategories(){
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
}