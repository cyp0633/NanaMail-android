package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.Mail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val mail = mutableStateOf(Mail())
    val exist = mutableStateOf(true)
    val expandMsgDetail = mutableStateOf(false)

    suspend fun fetch(uuid: String) = viewModelScope.launch(Dispatchers.IO) {
        val tempMail = AppDatabase.getDatabase(getApplication()).mailDao().getMail(uuid)
        exist.value = tempMail != null
        mail.value = tempMail ?: Mail()
    }

    fun setUnread() {}

    fun delete() {}
}