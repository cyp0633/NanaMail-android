package com.hnu.nanamail.data

import android.util.Log
import java.util.Properties
import javax.mail.AuthenticationFailedException
import javax.mail.MessagingException
import javax.mail.Session

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
            props["mail.smtp.auth"] = "true"
            when(encryptMethod) {
                "SSL" -> {
                    props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
                    props["mail.smtp.socketFactory.fallback"] = "false"
                    props["mail.smtp.socketFactory.port"] = portNumber
                }
                "TLS" -> {
                    props["mail.smtp.starttls.enable"] = "true"
                    props["mail.smtp.starttls.required"] = "true"
                }
            }
            val session = Session.getInstance(props)
            val transport = session.getTransport("smtp")
            transport.connect(server,portNumber,mailAddress,password)
            transport.close()
            return "success"
        } catch (e: AuthenticationFailedException) {
            Log.e("SmtpBackend", "Authentication failed")
            e.printStackTrace()
            return "Authentication failed"
        } catch (e: MessagingException) {
            Log.e("SmtpBackend", "MessagingException")
            e.printStackTrace()
            return "MessagingException"
        } catch (e: Exception) {
            Log.e("SmtpBackend", "Exception")
            e.printStackTrace()
            return "Exception"
        }
    }
}