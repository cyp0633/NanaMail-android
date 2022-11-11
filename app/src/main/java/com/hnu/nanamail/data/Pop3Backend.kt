package com.hnu.nanamail.data

import android.util.Log
import java.util.*
import javax.mail.AuthenticationFailedException
import javax.mail.MessagingException
import javax.mail.Session

object Pop3Backend {
    var mailAddress: String = ""
    var password: String = ""
    var server: String = ""
    var encryptMethod: String = ""
    var portNumber: Int = 0

    fun verify(): String {
        try {
            val props = Properties()
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
            val session = Session.getInstance(props)
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
}