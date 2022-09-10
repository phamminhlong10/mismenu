package com.android.mismenu.features.domain.repository

import androidx.lifecycle.LiveData
import com.android.mismenu.features.domain.data.entities.CartEntity
import com.android.mismenu.features.domain.data.entities.WishlistEntity

interface LocalRepository {
    suspend fun getAllCartItems(): LiveData<List<CartEntity>>
    suspend fun getAllWishlistItems(): LiveData<List<WishlistEntity>>

    suspend fun addItemToCart(cartEntity: CartEntity)
    suspend fun addItemToWishlist(wishlistEntity: WishlistEntity)

    suspend fun removeItemFromCart(cartEntity: CartEntity)
    suspend fun removeItemFromWishList(wishlistEntity: WishlistEntity)

    suspend fun clearCart()
}