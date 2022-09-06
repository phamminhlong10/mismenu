package com.android.mismenu.features.domain.entities

import com.google.firebase.firestore.DocumentReference

data class Category(
    val id: String? = null,
    val name: String? = null,
    val img: String? = null
)