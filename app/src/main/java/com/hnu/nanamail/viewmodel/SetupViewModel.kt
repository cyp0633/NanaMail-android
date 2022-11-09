package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.Pop3Backend
import com.hnu.nanamail.data.SmtpBackend

class SetupViewModel(application: Application):ViewModel() {
    fun verify(
        mailAddress: String,
        password: String,
        pop3Server: String,
        receiveEncryptMethod: String,
        receivePortNumber: String,
        smtpServer: String,
        sendEncryptMethod: String,
        sendPortNumber: String
    ): String {
        var result:String
        val smtpBackend = SmtpBackend(
            mailAddress,
            password,
            smtpServer,
            sendEncryptMethod,
            sendPortNumber.toInt()
        )
        result = smtpBackend.verify()
        if (result != "success") {
            return result
        }
        val pop3Backend = Pop3Backend(
            mailAddress,
            password,
            pop3Server,
            receiveEncryptMethod,
            receivePortNumber
        )
        result = pop3Backend.verify()
        return result
    }
}