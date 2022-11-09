package com.hnu.nanamail.viewmodel

import androidx.lifecycle.ViewModel
import com.hnu.nanamail.data.Pop3Backend
import com.hnu.nanamail.data.SmtpBackend

class SetupViewModel:ViewModel() {
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
            sendPortNumber
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