package com.android.mismenu.features.domain.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.mismenu.features.domain.entities.Product
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idProduct: String,
    val name: String,
    val price: Int,
    val image: String,
    var size: String,
): Parcelable

fun Product.asCartEntity() = CartEntity(
    idProduct = id!!,
    name = name!!,
    price = price!!,
    image = imageOfProduct?.get(0)?.urlImage!!,
    size = "",
)