package com.android.mismenu

import android.content.Context
import androidx.room.Room
import com.android.mismenu.features.domain.data.datasource.localDataSource.daos.CartDao
import com.android.mismenu.features.domain.data.datasource.localDataSource.Database
import com.android.mismenu.features.domain.data.datasource.localDataSource.daos.WishlistDao
import com.android.mismenu.features.domain.repository.Authentication
import com.android.mismenu.features.domain.repository.AuthenticationImpl
import com.android.mismenu.features.domain.data.datasource.remoteDataSource.FirestoreManagement
import com.android.mismenu.features.domain.data.repositories.LocalRepositoryImpl
import com.android.mismenu.features.domain.data.repositories.ManageCategoryImpl
import com.android.mismenu.features.domain.repository.LocalRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DIContainer{
    @Binds
    abstract fun bindAuthentication(authenticationImpl: AuthenticationImpl): Authentication

    @Binds
    abstract fun bindCategoryImpl(manageCategoryImpl: ManageCategoryImpl): FirestoreManagement

    @Binds
    abstract fun bindLocalRepositoryImpl(localRepositoryImpl: LocalRepositoryImpl): LocalRepository
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Singleton
    @Provides
    fun provideInstanceAuthentication() = Firebase.auth

    @Singleton
    @Provides
    fun provideInstanceFirestore() = Firebase.firestore

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, "database").build()
    }

    @Provides
    fun provideCartDao(database: Database): CartDao {
        return database.getCartDao()
    }


    @Provides
    fun provideWishlistDao(database: Database): WishlistDao {
        return database.getWishlistDao()
    }
}