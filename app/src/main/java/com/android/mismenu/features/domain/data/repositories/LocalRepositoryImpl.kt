package com.android.mismenu.features.domain.data.repositories

import androidx.lifecycle.LiveData
import com.android.mismenu.features.domain.data.datasource.localDataSource.daos.CartDao
import com.android.mismenu.features.domain.data.datasource.localDataSource.daos.WishlistDao
import com.android.mismenu.features.domain.data.entities.CartEntity
import com.android.mismenu.features.domain.data.entities.WishlistEntity
import com.android.mismenu.features.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(private val cartDao: CartDao, private val wishlistDao: WishlistDao): LocalRepository {
    override fun getAllCartItems(): LiveData<List<CartEntity>> {
        return cartDao.getAllItemInCart()
    }

    override fun getAllWishlistItems(): LiveData<List<WishlistEntity>> {
        return wishlistDao.getAllItemInWishList()
    }

    override fun addItemToCart(cartEntity: CartEntity) {
        cartDao.addItemToCart(cartEntity)
    }

    override fun addItemToWishlist(wishlistEntity: WishlistEntity) {
        wishlistDao.addItemToWishList(wishlistEntity)
    }

    override fun removeItemFromCart(cartEntity: CartEntity) {
        cartDao.removeItemFromCart(cartEntity)
    }

    override fun removeItemFromWishList(wishlistEntity: WishlistEntity) {
        wishlistDao.removeItemFromWishList(wishlistEntity)
    }

    override fun clearCart() {
        cartDao.clearCart()
    }
}