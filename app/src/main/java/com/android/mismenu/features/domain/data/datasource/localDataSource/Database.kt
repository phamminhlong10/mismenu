package com.android.mismenu.features.domain.data.datasource.localDataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.mismenu.features.domain.data.datasource.localDataSource.daos.CartDao
import com.android.mismenu.features.domain.data.datasource.localDataSource.daos.WishlistDao
import com.android.mismenu.features.domain.data.entities.CartEntity
import com.android.mismenu.features.domain.data.entities.WishlistEntity

@Database(entities = [CartEntity::class, WishlistEntity::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase(){
    abstract fun getCartDao(): CartDao

    abstract fun getWishlistDao(): WishlistDao
}

