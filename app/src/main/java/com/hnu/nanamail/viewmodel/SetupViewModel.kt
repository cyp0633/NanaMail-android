package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.Pop3Backend
import com.hnu.nanamail.data.SmtpBackend
import com.hnu.nanamail.data.User

class SetupViewModel(application: Application) : AndroidViewModel(application) {
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
    var dialogText = ""

    fun verify(): String {
        var result: String
        if (mailAddress.value == "" || password.value == "" || pop3Server.value == "" || receiveEncryptMethod.value == "" || receivePortNumber.value == "" || smtpServer.value == "" || sendEncryptMethod.value == "" || sendPortNumber.value == "") {
            return "请填写完整信息"
        }
        val smtpBackend = SmtpBackend(
            mailAddress.value,
            password.value,
            smtpServer.value,
            sendEncryptMethod.value,
            sendPortNumber.value.toInt()
        )
        result = smtpBackend.verify()
        if (result != "success") {
            return "SMTP 验证错误：$result"
        }
        val pop3Backend = Pop3Backend(
            mailAddress.value,
            password.value,
            pop3Server.value,
            receiveEncryptMethod.value,
            receivePortNumber.value.toInt()
        )
        result = pop3Backend.verify()
        if(result!="success"){
            return "POP3 验证错误：$result"
        }
        // 保存用户信息，目前只有一个用户，所以把以前的先删掉
        val db = AppDatabase.getDatabase(getApplication())
        val existUser = db.userDao().getUser()
        if(existUser!=null){
            db.userDao().deleteUser(existUser)
        }
        db.userDao().insertUser(
            User(
                mailAddress.value,
                password.value,
                pop3Server.value,
                receiveEncryptMethod.value,
                receivePortNumber.value.toInt(),
                smtpServer.value,
                sendEncryptMethod.value,
                sendPortNumber.value.toInt()
            )
        )
        return "success"
    }
}