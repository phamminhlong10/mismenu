package com.android.mismenu.features.presentaion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mismenu.features.domain.repository.Authentication
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val auth: Authentication): ViewModel() {
    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?>
        get() = _user

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    init {
        _user.value = auth.currentUser()
    }


    fun signIn(){
        viewModelScope.launch {
            auth.signInWithEmailPassword(email.value.toString(), password.value.toString())
            delay(1000L)
            _user.value = auth.currentUser()
        }
    }
}