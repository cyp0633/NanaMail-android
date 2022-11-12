package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.Mail
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val mail = mutableStateOf(Mail())
    val exist = mutableStateOf(true)
    val expandMsgDetail = mutableStateOf(false)

    fun fetch(uuid: String): Mail {
        viewModelScope.launch {
            val tempMail = AppDatabase.getDatabase(getApplication()).mailDao().getMail(uuid)
            exist.value = tempMail != null
            mail.value = tempMail ?: Mail()
        }
        return mail.value
    }

    fun setUnread() {}

    fun delete() {}
}