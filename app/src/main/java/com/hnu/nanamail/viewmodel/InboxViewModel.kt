package com.hnu.nanamail.viewmodel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
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
    val page = mutableStateOf(0)
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
        val db = AppDatabase.getDatabase(getApplication())
        viewModelScope.launch(Dispatchers.IO) {
            val fetchList = Pop3Backend.fetchInbox()
            for (mail in fetchList) {
                try {
                    db.mailDao().insertMail(mail)
                } catch (e: SQLiteConstraintException) {
                    Log.i("InboxViewModel", "fetchMail: removed duplicate mail")
                }
            }
            mailList =
                AppDatabase.getDatabase(getApplication()).mailDao()
                    .getMailListLiveDataByPage(
                        page.value,
                        MailType.INBOX
                    )
        }
    }
}