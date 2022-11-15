package com.hnu.nanamail.viewmodel

import android.app.Application
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

    fun sendMail() = viewModelScope.launch(Dispatchers.IO) {
        val db = AppDatabase.getDatabase(getApplication())
        val mail = Mail(
            uuid = UUID.randomUUID().toString(), // 此处仍然可以用 UUID，因为不会重新抓取邮件
            account = username,
            sender = username,
            senderAddress = username,
            recipientTo = recipient.value,
            recipientCc = recipientCc.value,
            subject = subject.value,
            content = content.value,
            type = MailType.OUTBOX,
            preview = content.value.substring(0, 50),
            time = System.currentTimeMillis(),
        )
        db.mailDao().insertMail(mail)
        // 发送邮件
        val sent = SmtpBackend.sendMail(
            username,
            recipient.value,
            recipientCc.value,
            recipientBcc.value,
            subject.value,
            content.value
        )
        // 若发送成功，写入已发送
        if (sent) {
            db.mailDao().moveToSent(mail.uuid, MailType.SENT)
        }
    }
}