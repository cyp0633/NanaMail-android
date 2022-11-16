package com.hnu.nanamail.data

import android.util.Log
import com.hnu.nanamail.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.activation.DataHandler
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import javax.mail.util.ByteArrayDataSource

object SmtpBackend {
    var mailAddress: String = ""
    var password: String = ""
    var server: String = ""
    var encryptMethod: String = ""
    var portNumber: Int = 0

    /**
     * 从 User.currentUser 中获取服务器信息
     */
    private fun init() {
        mailAddress = User.currentUser?.mailAddress ?: ""
        password = User.currentUser?.password ?: ""
        server = User.currentUser?.smtpServer ?: ""
        encryptMethod = User.currentUser?.sendEncryptMethod ?: ""
        portNumber = User.currentUser?.sendPortNumber ?: 0
        Log.i(
            "SmtpBackend",
            "init backend as: $mailAddress $password $server $encryptMethod $portNumber"
        )
    }

    suspend fun verify(): String = withContext(Dispatchers.IO) {
        try {
            val props = Properties()
            props["mail.transport.protocol"] = "smtp"
            props["mail.smtp.host"] = server
//            props["mail.smtp.auth"] = "true"
            props["mail.smtp.port"] = portNumber
            if (encryptMethod != "") {
                props["mail.smtp.starttls.enable"] = "true"
            }
            val auth = object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(mailAddress, password)
                }
            }
            val session = Session.getInstance(props, auth)
            if (BuildConfig.DEBUG) {
                session.debug = true
            }
            val transport = session.transport
            transport.connect(server, portNumber, mailAddress, password)
            transport.close()
            return@withContext "success"
        } catch (e: AuthenticationFailedException) {
            Log.e("SmtpBackend", "Authentication failed: ${e.message}")
            e.printStackTrace()
            return@withContext "Authentication failed: ${e.message}"
        } catch (e: MessagingException) {
            Log.e("SmtpBackend", "MessagingException: ${e.message}")
            e.printStackTrace()
            return@withContext "MessagingException: ${e.message}"
        } catch (e: Exception) {
            Log.e("SmtpBackend", "Exception: ${e.message}")
            e.printStackTrace()
            return@withContext "Exception: ${e.message}"
        }
    }

    /**
     * 发送邮件
     * @param username 用户名
     * @param recipient 收件人
     * @param recipientCc 抄送人
     * @param recipientBcc 密送人
     * @param subject 主题
     * @param content 内容
     */
    fun sendMail(
        username: String,
        recipient: String,
        recipientCc: String,
        recipientBcc: String,
        subject: String,
        content: String
    ): Boolean {
        try {
            if (mailAddress == "" || password == "" || server == "" || portNumber == 0) {
                init()
            }
            val props = Properties()
            props["mail.transport.protocol"] = "smtp"
            props["mail.smtp.host"] = server
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.port"] = portNumber
            if (encryptMethod != "") {
                props["mail.smtp.starttls.enable"] = "true"
            }
            val auth = object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(mailAddress, password)
                }
            }
            val session = Session.getInstance(props, auth)
            val transport = session.transport
            transport.connect()
            val message = MimeMessage(session)
            val handler = DataHandler(ByteArrayDataSource(content, "text/plain"))
            message.sender = InternetAddress(username)
            message.subject = subject
            message.dataHandler = handler
            if (recipient.indexOf(",") != -1) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient))
            } else {
                message.setRecipient(Message.RecipientType.TO, InternetAddress(recipient))
            }
            if (recipientCc.indexOf(",") != -1) {
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(recipientCc))
            }
            if (recipientBcc.indexOf(",") != -1) {
                message.setRecipients(
                    Message.RecipientType.BCC,
                    InternetAddress.parse(recipientBcc)
                )
            }
            transport.sendMessage(message, message.allRecipients)
            transport.close()
            return true
        } catch (e: MessagingException) {
            Log.e("SmtpBackend", "MessagingException: ${e.message}")
            e.printStackTrace()
            return false
        } catch (e: Exception) {
            Log.e("SmtpBackend", "Exception: ${e.message}")
            e.printStackTrace()
            return false
        }
    }
}