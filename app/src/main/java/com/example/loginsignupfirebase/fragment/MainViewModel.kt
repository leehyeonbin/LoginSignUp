package com.example.loginsignupfirebase.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val userEmail: LiveData<String> get() = _userEmail
    private val _userEmail = MutableLiveData<String>()

    fun setUserEmail(email: String) {
        _userEmail.value = email
    }
}