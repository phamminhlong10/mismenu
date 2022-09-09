package com.android.mismenu

import com.android.mismenu.features.domain.repository.Authentication
import com.android.mismenu.features.domain.repository.AuthenticationImpl
import com.android.mismenu.features.domain.repository.FirestoreManagement
import com.android.mismenu.features.domain.data.ManageCategoryImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DIContainer{
    @Binds
    abstract fun bindAuthentication(authenticationImpl: AuthenticationImpl): Authentication

    @Binds
    abstract fun bindCategoryImpl(manageCategoryImpl: ManageCategoryImpl): FirestoreManagement
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
}