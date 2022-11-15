package com.hnu.nanamail.data

import android.util.Log
import com.hnu.nanamail.util.parseMessagesIntoMails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.mail.*

object Pop3Backend {
    var mailAddress: String = ""
    var password: String = ""
    var server: String = ""
    var encryptMethod: String = ""
    var portNumber: Int = 0
    private var props = Properties()
    private lateinit var session: Session

    /**
     * 从 User.currentUser 中获取服务器信息
     */
    private fun init() {
        mailAddress = User.currentUser?.mailAddress ?: ""
        password = User.currentUser?.password ?: ""
        server = User.currentUser?.pop3Server ?: ""
        encryptMethod = User.currentUser?.receiveEncryptMethod ?: ""
        portNumber = User.currentUser?.receivePortNumber ?: 0
        props["mail.transport.protocol"] = "pop3"
        props["mail.pop3.host"] = server
        props["mail.pop3.port"] = portNumber
        if (encryptMethod != "") {
            props["mail.pop3.starttls.enable"] = "true"
        }
        session = Session.getInstance(props)
        Log.i(
            "Pop3Backend",
            "init backend as: $mailAddress $password $server $encryptMethod $portNumber"
        )
    }

    suspend fun verify(): String = withContext(Dispatchers.IO) {
        try {
            props["mail.store.protocol"] = "pop3"
            props["mail.pop3.auth"] = "true"
            props["mail.pop3.host"] = server
            props["mail.pop3.port"] = portNumber
            if (encryptMethod != "") {
                props["mail.pop3.starttls.enable"] = "true"
            }
            val auth = object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(SmtpBackend.mailAddress, SmtpBackend.password)
                }
            }
            session = Session.getInstance(props, auth)
            val store = session.getStore("pop3")
            store.connect(server, portNumber, mailAddress, password)
            store.close()
            return@withContext "success"
        } catch (e: AuthenticationFailedException) {
            Log.e("Pop3Backend", "Authentication failed")
            e.printStackTrace()
            return@withContext "Authentication failed"
        } catch (e: MessagingException) {
            Log.e("Pop3Backend", "MessagingException")
            e.printStackTrace()
            return@withContext "MessagingException"
        } catch (e: Exception) {
            Log.e("Pop3Backend", "Exception")
            e.printStackTrace()
            return@withContext "Exception"
        }
    }

    // 从收件箱拉取邮件
    suspend fun fetchInbox(): List<Mail> {
        val store: Store
        val emailFolder: Folder
        if (mailAddress == "" || password == "" || server == "" || encryptMethod == "" || portNumber == 0) {
            init()
        }
        if (!::session.isInitialized) {
            verify()
        }
        try {
            store = session.getStore("pop3")
            store.connect(mailAddress, password)
            emailFolder = store.getFolder("INBOX")
            emailFolder.open(Folder.READ_WRITE)
            val msg = emailFolder.messages
            // TODO: 改善拉取逻辑，不拉取存在的邮件并设置上限
            val mailList = parseMessagesIntoMails(msg, MailType.INBOX)
            emailFolder.close(false) // 关闭邮件夹，但不删除邮件
            store.close()
            return mailList
        } catch (e: MessagingException) {
            Log.e("Pop3Backend", "MessagingException, ${e.message}")
            e.printStackTrace()
        } catch (e: AuthenticationFailedException) {
            Log.e("Pop3Backend", "AuthenticationFailedException, ${e.message}")
            e.printStackTrace()
        } catch (e: Exception) {
            Log.e("Pop3Backend", "Exception, ${e.message}")
            e.printStackTrace()
        }
        return listOf()
    }
}