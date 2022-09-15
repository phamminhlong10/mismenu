package com.android.mismenu.features.domain.data.datasource.localDataSource.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.mismenu.features.domain.data.entities.CartEntity

@Dao
interface CartDao {
    @Query("SELECT * FROM CartEntity")
    fun getAllItemInCart(): LiveData<List<CartEntity>>

    @Insert()
    fun addItemToCart(cartEntity: CartEntity)

    @Delete
    fun removeItemFromCart(cartEntity: CartEntity)

    @Query("DELETE FROM CartEntity")
    fun clearCart()
}