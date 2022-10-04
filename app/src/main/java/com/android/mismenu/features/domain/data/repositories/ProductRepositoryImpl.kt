package com.android.mismenu.features.domain.data.repositories

import com.android.mismenu.features.domain.entities.Category
import com.android.mismenu.features.domain.entities.Product
import com.android.mismenu.features.domain.repository.ProductRepository
import com.android.mismenu.features.domain.repository.ResultsCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor (private val fireStore: FirebaseFirestore) : ProductRepository {
    override suspend fun getNewArrivalOfProducts(callback: ResultsCallback) {
        withContext(Dispatchers.IO){
            var listProduct = mutableListOf<Product>()
            fireStore.collection("product").orderBy("timeAdded", Query.Direction.DESCENDING).limit(10).get().addOnSuccessListener { collectionsSnapshot  ->
                for (item in collectionsSnapshot){
                    listProduct.add(item.toObject<Product>())
                }
                callback.onListProductsCallback(listProduct)
            }
        }
    }

    override suspend fun getAllProducts(callback: ResultsCallback) {
        withContext(Dispatchers.IO){
            var listProduct = mutableListOf<Product>()
            fireStore.collection("product").orderBy("timeAdded", Query.Direction.DESCENDING).get().addOnSuccessListener { collectionsSnapshot  ->
                for (item in collectionsSnapshot){
                    listProduct.add(item.toObject<Product>())
                }
                callback.onListProductsCallback(listProduct)
            }
        }
    }

    override suspend fun getProductDetail(id: String, callback: ResultsCallback) {
        withContext(Dispatchers.IO){
            val ref = fireStore.collection("product").document(id)
            ref.get().addOnSuccessListener {
                    documentSnapshot -> val product = documentSnapshot.toObject<Product>()
                callback.onProductCallback(product)
            }
        }
    }
}