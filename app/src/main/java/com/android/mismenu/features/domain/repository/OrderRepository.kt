package com.android.mismenu.features.domain.repository

import com.android.mismenu.features.domain.data.entities.CartEntity
import com.android.mismenu.features.domain.entities.Order

interface OrderRepository {
    fun sentOrder(order: Order)
}