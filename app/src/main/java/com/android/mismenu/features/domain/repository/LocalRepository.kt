package com.android.mismenu.features.domain.repository

import androidx.lifecycle.LiveData
import com.android.mismenu.features.domain.data.entities.CartEntity
import com.android.mismenu.features.domain.data.entities.WishlistEntity

interface LocalRepository {
    fun getAllCartItems(): LiveData<List<CartEntity>>
    fun getAllWishlistItems(): LiveData<List<WishlistEntity>>

    fun addItemToCart(cartEntity: CartEntity)
    fun addItemToWishlist(wishlistEntity: WishlistEntity)

    fun removeItemFromCart(cartEntity: CartEntity)
    fun removeItemFromWishList(wishlistEntity: WishlistEntity)

    fun clearCart()
}