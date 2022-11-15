package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.Mail
import com.hnu.nanamail.data.MailType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val mail = mutableStateOf(Mail())
    val exist = mutableStateOf(true)
    val expandMsgDetail = mutableStateOf(false)

    suspend fun fetch(uuid: String) = viewModelScope.launch(Dispatchers.IO) {
        val db = AppDatabase.getDatabase(getApplication())
        val tempMail = db.mailDao().getMail(uuid)
        exist.value = tempMail != null
        mail.value = tempMail ?: Mail()
        if (mail.value.type == MailType.INBOX && !mail.value.isRead) {
            db.mailDao().markAsRead(uuid)
        }
    }

    fun setUnread() = viewModelScope.launch(Dispatchers.IO) {
        val db = AppDatabase.getDatabase(getApplication())
        db.mailDao().markAsRead(mail.value.uuid, false)
    }

    fun delete() = viewModelScope.launch(Dispatchers.IO) {
        val db = AppDatabase.getDatabase(getApplication())
        db.mailDao().deleteMails(mail.value)
    }
}