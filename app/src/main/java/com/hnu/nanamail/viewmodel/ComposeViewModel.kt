package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.Mail
import com.hnu.nanamail.data.MailType
import com.hnu.nanamail.data.SmtpBackend
import com.hnu.nanamail.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ComposeViewModel(application: Application) : AndroidViewModel(application) {
    val showRecipientCc = mutableStateOf(false)
    val username = User.currentUser!!.mailAddress
    val recipient = mutableStateOf("")
    val recipientCc = mutableStateOf("")
    val recipientBcc = mutableStateOf("")
    val subject = mutableStateOf("")
    val content = mutableStateOf("")

    fun sendMail() {
        val mail = Mail(
            uuid = UUID.randomUUID().toString(),
            account = username,
            sender = username,
            senderAddress = username,
            recipientTo = recipient.value,
            recipientCc = recipientCc.value,
            subject = subject.value,
            content = content.value,
            type = MailType.SENT,
            preview = content.value.substring(0, 50),
            time = System.currentTimeMillis(),
        )
        viewModelScope.launch(Dispatchers.IO) {
            // 发送邮件
            SmtpBackend.sendMail(
                username,
                recipient.value,
                recipientCc.value,
                recipientBcc.value,
                subject.value,
                content.value
            )
            // 写入已发送（发送失败怎么办？）
            AppDatabase.getDatabase(getApplication()).mailDao().insertMails(mail)
        }
    }
}