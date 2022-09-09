package com.android.mismenu.features.domain.entities

import android.os.Parcelable
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.*

@Parcelize
data class Product(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Int? = null,
    @field:JvmField
    val isSoldOut: Boolean? = null,
    val imageOfProduct: @RawValue List<Image>? =null,
    val category: @RawValue DocumentReference? = null,
    @ServerTimestamp
    val timeAdded: Date? = null,
) : Parcelable