package com.hnu.nanamail.data

import android.app.Application
import android.util.Log
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.util.*
import java.util.*
import javax.mail.AuthenticationFailedException
import javax.mail.Folder
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.Store
import javax.mail.internet.InternetAddress

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
        val mailList = listOf<Mail>()
        try {
            store = session.getStore("pop3")
            store.connect()
            emailFolder = store.getFolder("INBOX")
            emailFolder.open(Folder.READ_WRITE)
            val msg = emailFolder.messages
            // TODO: 改善拉取逻辑，不拉取存在的邮件并设置上限
            for (m in msg) {
                val isRead = isRead(m)
                val hasAttachment = hasAttachment(m)
                val content = parseContent(m)
                // TODO: 解析图片
                // 发送者和其邮箱
                val fromAddress = (m.from[0] as InternetAddress).address
                val from = (m.from[0] as InternetAddress).personal
                val recipientTo = parseRecipientTo(m)
                val recipientCc = parseRecipientCc(m)
                val uid = m.messageNumber
                val subject = m.subject
                val preview = if (content.length > 100) content.substring(0, 100) else content
                val mail = Mail(
                    uuid = UUID.randomUUID().toString(),
                    account = mailAddress,
                    sender = from,
                    senderAddress = fromAddress,
                    recipientTo = recipientTo,
                    recipientCc = recipientCc,
                    subject = subject,
                    content = content,
                    preview = preview,
                    isRead = isRead,
                    hasAttachment = hasAttachment,
                    attachmentDownloaded = false,
                    uid = uid,
                    type = MailType.INBOX,
                    time = m.sentDate.time
                )
                mailList.plus(mail)
            }
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