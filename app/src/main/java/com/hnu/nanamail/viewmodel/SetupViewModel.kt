package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.Pop3Backend
import com.hnu.nanamail.data.SmtpBackend

class SetupViewModel(application: Application):AndroidViewModel(application) {
    // 输入框内容
    val mailAddress = mutableStateOf("")
    val password = mutableStateOf("")
    val pop3Server = mutableStateOf("")
    val receiveEncryptMethod = mutableStateOf("")
    val receivePortNumber = mutableStateOf("")
    val smtpServer = mutableStateOf("")
    val sendEncryptMethod = mutableStateOf("")
    val sendPortNumber = mutableStateOf("")

    val modifiedPop3Server = mutableStateOf(false)
    val modifiedSmtpServer = mutableStateOf(false)

    // 提示框内容
    var showDialog = mutableStateOf(false)
    var onDismissRequest = {}
    val dialogText = ""

    fun verify(): String {
        var result:String
        val smtpBackend = SmtpBackend(
            mailAddress.value,
            password.value,
            smtpServer.value,
            sendEncryptMethod.value,
            sendPortNumber.value.toInt()
        )
        result = smtpBackend.verify()
        if (result != "success") {
            return result
        }
        val pop3Backend = Pop3Backend(
            mailAddress.value,
            password.value,
            pop3Server.value,
            receiveEncryptMethod.value,
            receivePortNumber.value.toInt()
        )
        result = pop3Backend.verify()
        return result
    }
}