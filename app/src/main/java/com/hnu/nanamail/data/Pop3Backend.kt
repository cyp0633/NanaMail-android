package com.hnu.nanamail.data

import android.util.Log
import com.hnu.nanamail.util.parseMessagesIntoMails
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

    fun verify(): String {
        try {
//            val props = Properties()
            props["mail.pop3.auth"] = "true"
            when (encryptMethod) {
                "SSL" -> {
                    props["mail.pop3.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
                    props["mail.pop3.socketFactory.fallback"] = "false"
                    props["mail.pop3.socketFactory.port"] = portNumber
                }
                "TLS" -> {
                    props["mail.pop3.starttls.enable"] = "true"
                    props["mail.pop3.starttls.required"] = "true"
                }
            }
            session = Session.getInstance(props)
            val store = session.getStore("pop3")
            store.connect(server, portNumber, mailAddress, password)
            store.close()
            return "success"
        } catch (e: AuthenticationFailedException) {
            Log.e("Pop3Backend", "Authentication failed")
            e.printStackTrace()
            return "Authentication failed"
        } catch (e: MessagingException) {
            Log.e("Pop3Backend", "MessagingException")
            e.printStackTrace()
            return "MessagingException"
        } catch (e: Exception) {
            Log.e("Pop3Backend", "Exception")
            e.printStackTrace()
            return "Exception"
        }
    }

    // 从收件箱拉取邮件
    fun fetchInbox(): List<Mail> {
        val store: Store
        val emailFolder: Folder
        try {
            store = session.getStore("pop3")
            store.connect(server, portNumber, mailAddress, password)
            emailFolder = store.getFolder("INBOX")
            emailFolder.open(Folder.READ_WRITE)
            val msg = emailFolder.messages
            // TODO: 改善拉取逻辑，不拉取存在的邮件并设置上限
            return parseMessagesIntoMails(msg, MailType.INBOX)
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