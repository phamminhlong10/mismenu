package com.android.mismenu.features.domain.repository

import android.content.ContentValues
import android.util.Log
import com.android.mismenu.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

interface Authentication {
    fun createUserWithEmailPassword(email: String, password: String)

    fun signInWithEmailPassword(email: String, password: String)

    fun signOut()

    fun currentUser(): FirebaseUser?
}

class AuthenticationImpl @Inject constructor(private val auth: FirebaseAuth) : Authentication {
    override fun createUserWithEmailPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity()){
                task -> if(task.isSuccessful){
            Log.d(ContentValues.TAG, "createUserWithEmail:success")
            //val user = auth.currentUser
        }else{
            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
        }
        }
    }

    override fun signInWithEmailPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity()){
                task -> if(task.isSuccessful){
            Log.d(ContentValues.TAG, "signInUserWithEmail:success")
            val user = auth.currentUser
        }else{
            Log.w(ContentValues.TAG,"signInUserWithEmail:failure", task.exception)
        }
        }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun currentUser(): FirebaseUser? {
        return auth.currentUser
    }
}