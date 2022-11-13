package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.Pop3Backend
import com.hnu.nanamail.data.SmtpBackend
import com.hnu.nanamail.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    var dialogText = ""
    private var result = ""
    var proceed = mutableStateOf(false) // 验证成功，可以前往下一步

    fun verify() {
        dialogText = ""
        if (mailAddress.value == "" || password.value == "" || pop3Server.value == "" || receivePortNumber.value == "" || smtpServer.value == "" || sendPortNumber.value == "") {
            dialogText = "请填写完整信息"
            proceed.value = false
            showDialog.value = true
            return
        }

        SmtpBackend.mailAddress = mailAddress.value
        SmtpBackend.password = password.value
        SmtpBackend.server = smtpServer.value
        SmtpBackend.encryptMethod = sendEncryptMethod.value
        SmtpBackend.portNumber = sendPortNumber.value.toInt()

        viewModelScope.launch {
            result = SmtpBackend.verify()
        }
        if (result != "success") {
            dialogText = "SMTP 验证错误：$result"
        }

        Pop3Backend.mailAddress = mailAddress.value
        Pop3Backend.password = password.value
        Pop3Backend.server = pop3Server.value
        Pop3Backend.encryptMethod = receiveEncryptMethod.value
        Pop3Backend.portNumber = receivePortNumber.value.toInt()

        viewModelScope.launch { result = Pop3Backend.verify() }
        if (dialogText != "") { // 上面还有错误的话就换行
            dialogText = dialogText.plus("\n")
        }
        if (result != "success") { // 接上 POP3 的错误
            dialogText = dialogText.plus("POP3 验证错误：$result")
        }

        // 有错误的话就不再进行数据库访问了
        if (dialogText != "") {
            proceed.value = false
            showDialog.value = true
            return
        }
        // 保存用户信息，目前只有一个用户，所以把以前的先删掉
        val user = User(
            mailAddress.value,
            password.value,
            pop3Server.value,
            receiveEncryptMethod.value,
            receivePortNumber.value.toInt(),
            smtpServer.value,
            sendEncryptMethod.value,
            sendPortNumber.value.toInt()
        )
        viewModelScope.launch {
            replaceNewUser(user)
        }
        User.currentUser = user
        dialogText = "验证成功"
        proceed.value = true
        showDialog.value = true
    }

    private suspend fun replaceNewUser(user: User) = withContext(Dispatchers.IO) {
        val db = AppDatabase.getDatabase(getApplication())
        val existUser = db.userDao().getUser()
        if (existUser != null) {
            db.userDao().deleteUser(existUser)
        }
        db.userDao().insertUser(user)
    }
}
