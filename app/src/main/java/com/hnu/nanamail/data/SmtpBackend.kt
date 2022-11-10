package com.hnu.nanamail.data

import android.util.Log
import com.sun.mail.util.MailSSLSocketFactory
import java.util.Properties
import javax.mail.AuthenticationFailedException
import javax.mail.Authenticator
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport

class SmtpBackend(
    private var mailAddress: String,
    private var password: String,
    private var server: String,
    private var encryptMethod: String,
    private var portNumber: Int
) {
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
}