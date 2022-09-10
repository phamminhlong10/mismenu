package com.android.mismenu.features.domain.data.repositories

import androidx.lifecycle.LiveData
import com.android.mismenu.features.domain.data.datasource.localDataSource.daos.CartDao
import com.android.mismenu.features.domain.data.datasource.localDataSource.daos.WishlistDao
import com.android.mismenu.features.domain.data.entities.CartEntity
import com.android.mismenu.features.domain.data.entities.WishlistEntity
import com.android.mismenu.features.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(private val cartDao: CartDao, private val wishlistDao: WishlistDao): LocalRepository {
    override suspend fun getAllCartItems(): LiveData<List<CartEntity>> {
        return cartDao.getAllItemInCart()
    }

    override suspend fun getAllWishlistItems(): LiveData<List<WishlistEntity>> {
        return wishlistDao.getAllItemInWishList()
    }

    override suspend fun addItemToCart(cartEntity: CartEntity) {
        cartDao.addItemToCart(cartEntity)
    }

    override suspend fun addItemToWishlist(wishlistEntity: WishlistEntity) {
        wishlistDao.addItemToWishList(wishlistEntity)
    }

    override suspend fun removeItemFromCart(cartEntity: CartEntity) {
        cartDao.removeItemFromCart(cartEntity)
    }

    override suspend fun removeItemFromWishList(wishlistEntity: WishlistEntity) {
        wishlistDao.removeItemFromWishList(wishlistEntity)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}