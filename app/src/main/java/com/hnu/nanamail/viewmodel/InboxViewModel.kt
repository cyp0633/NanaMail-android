package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InboxViewModel(application: Application) : AndroidViewModel(application) {
    fun checkLogin(): Boolean {
        var user: User? = null
        viewModelScope.launch(Dispatchers.IO) {
            user = AppDatabase.getDatabase(getApplication()).userDao().getUser()
        }
        return user != null
    }
}