package com.android.mismenu.features.domain.data.datasource.localDataSource.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.mismenu.features.domain.data.entities.WishlistEntity

@Dao
interface WishlistDao {
    @Query("SELECT * FROM WishlistEntity")
    fun getAllItemInWishList(): LiveData<List<WishlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItemToWishList(wishlistEntity: WishlistEntity)

    @Delete
    fun removeItemFromWishList(wishlistEntity: WishlistEntity)
}
