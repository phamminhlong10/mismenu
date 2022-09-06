package com.android.mismenu.features.domain.entities

import com.google.firebase.firestore.DocumentReference

data class Product(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Int? = null,
    @field:JvmField
    val isSoldOut: Boolean? = null,
    val imageOfProduct: List<Image>? =null,
    val category: DocumentReference? = null
)