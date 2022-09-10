package com.android.mismenu.features.domain.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.mismenu.features.domain.entities.Product
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class WishlistEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val price: Int,
    val image: String
):Parcelable

fun Product.asWishlistEntity() = WishlistEntity(
    id = id!!,
    name = name!!,
    price = price!!,
    image = imageOfProduct?.get(0)?.urlImage!!
)