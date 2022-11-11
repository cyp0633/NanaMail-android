package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.Mail
import com.hnu.nanamail.data.Pop3Backend
import com.hnu.nanamail.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InboxViewModel(application: Application) : AndroidViewModel(application) {
    var mailList = mutableStateListOf<Mail>()
    val page = mutableStateOf(1)
//    val db = AppDatabase.getDatabase(getApplication())

    fun checkLogin(): Boolean {
        var user: User? = null
        viewModelScope.launch(Dispatchers.IO) {
            user = AppDatabase.getDatabase(getApplication()).userDao().getUser()
        }
        return user != null
    }

    fun getMailList() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchList = Pop3Backend.fetchInbox()
            AppDatabase.getDatabase(getApplication()).mailDao().insertMails(fetchList)
            mailList =
                AppDatabase.getDatabase(getApplication()).mailDao().getMailListByPage(page.value)
                    .toMutableStateList()
        }
    }
}