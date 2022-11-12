package com.hnu.nanamail.data

import android.util.Log
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

    fun verify(): String {
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
            val transport = session.transport
            transport.connect()
            transport.close()
            return "success"
        } catch (e: AuthenticationFailedException) {
            Log.e("SmtpBackend", "Authentication failed: ${e.message}")
            e.printStackTrace()
            return "Authentication failed: ${e.message}"
        } catch (e: MessagingException) {
            Log.e("SmtpBackend", "MessagingException: ${e.message}")
            e.printStackTrace()
            return "MessagingException: ${e.message}"
        } catch (e: Exception) {
            Log.e("SmtpBackend", "Exception: ${e.message}")
            e.printStackTrace()
            return "Exception: ${e.message}"
        }
    }

    fun sendMail(
        username: String,
        recipient: String,
        recipientCc: String,
        recipientBcc: String,
        subject: String,
        content: String
    ) {
        try {
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
        } catch (e: MessagingException) {
            Log.e("SmtpBackend", "MessagingException: ${e.message}")
            e.printStackTrace()
        }
    }
}