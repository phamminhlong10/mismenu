package com.android.mismenu.features.domain.entities

import com.android.mismenu.features.domain.data.entities.CartEntity

data class Order(
    var id: String? = null,
    var name: String? = null,
    var address: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var ItemOrder: List<CartEntity>? = null,
    var summary: Int? = null,
    @field:JvmField
    var isRead: Boolean? = null
)