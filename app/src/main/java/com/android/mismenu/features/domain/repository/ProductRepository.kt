package com.android.mismenu.features.domain.repository

import com.android.mismenu.features.domain.entities.Product

interface ProductRepository {
    suspend fun getNewArrivalOfProducts(callback: ResultsCallback)
    suspend fun getAllProducts(callback: ResultsCallback)
    suspend fun getProductDetail(id: String, callback: ResultsCallback)
}

interface ResultsCallback{
    fun onListProductsCallback(list: List<Product>)
    fun onProductCallback(product: Product?)
}