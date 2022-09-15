package com.android.mismenu.features.domain.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.android.mismenu.features.domain.data.entities.CartEntity
import com.android.mismenu.features.domain.entities.Order
import com.android.mismenu.features.domain.repository.Authentication
import com.android.mismenu.features.domain.repository.OrderRepository
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val fireStore: FirebaseFirestore, private val authentication: Authentication) : OrderRepository {
    override fun sentOrder(order: Order) {
        val ref = fireStore.collection("user").document(authentication.currentUser()!!.uid).collection("history_orders").document()
        order.apply {
            this.id = ref.id
            this.email = authentication.currentUser()!!.email
        }
        ref.set(order)
        order.apply {
            order.isRead = false
        }
        fireStore.collection("order").document(ref.id).set(order)
    }
}