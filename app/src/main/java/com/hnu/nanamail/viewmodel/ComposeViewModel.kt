package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComposeViewModel(application: Application): AndroidViewModel(application) {
    val showRecipientCc = mutableStateOf(false)
    val username = mutableStateOf("")
    val recipient = mutableStateOf("")
    val recipientCc = mutableStateOf("")
    val recipientBcc = mutableStateOf("")
    val subject = mutableStateOf("")
    val content = mutableStateOf("")

    fun updateUser() {
        var user: User
        viewModelScope.launch(Dispatchers.IO) {
            user = AppDatabase.getDatabase(getApplication()).userDao().getUser()!!
            username.value = user.mailAddress
        }
    }
    fun sendMail() {}
}