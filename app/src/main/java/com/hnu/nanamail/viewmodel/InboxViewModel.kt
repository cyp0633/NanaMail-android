package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hnu.nanamail.dao.AppDatabase

class InboxViewModel(application: Application) : AndroidViewModel(application) {
    fun checkLogin(): Boolean {
        val user = AppDatabase.getDatabase(getApplication()).userDao().getUser() ?: return false
        return true
    }
}