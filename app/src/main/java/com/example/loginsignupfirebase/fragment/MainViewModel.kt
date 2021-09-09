package com.example.loginsignupfirebase.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // 읽기만 가능하여 변화를 감지하는 감시자
    val userEmail: LiveData<String> get() = _userEmail
    // 변경 사항을 수정하는 곳
    private val _userEmail = MutableLiveData<String>()

    fun setUserEmail(email: String) {
        _userEmail.value = email
    }
}