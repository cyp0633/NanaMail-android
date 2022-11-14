package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.Mail
import com.hnu.nanamail.data.MailType
import com.hnu.nanamail.data.Pop3Backend
import com.hnu.nanamail.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InboxViewModel(application: Application) : AndroidViewModel(application) {
    val page = mutableStateOf(1)
    var mailList: LiveData<List<Mail>> = MutableLiveData()

    fun checkLogin(): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            User.currentUser = AppDatabase.getDatabase(getApplication()).userDao().getUser()
        }
        return User.currentUser != null
    }

    // 从数据库中获取邮件（不联网）
    fun getMailList() {
        viewModelScope.launch(Dispatchers.IO) {
            mailList =
                AppDatabase.getDatabase(getApplication()).mailDao()
                    .getMailListLiveDataByPage(
                        page.value,
                        MailType.INBOX
                    )
        }
    }

    // 从远程收件箱获取邮件
    fun fetchMail() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchList = Pop3Backend.fetchInbox()
            AppDatabase.getDatabase(getApplication()).mailDao()
                .insertMails(*fetchList.toTypedArray())
            mailList =
                AppDatabase.getDatabase(getApplication()).mailDao()
                    .getMailListLiveDataByPage(
                        page.value,
                        MailType.INBOX
                    )
        }
    }
}