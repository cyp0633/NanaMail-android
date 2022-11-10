package com.hnu.nanamail.data

import org.junit.Test

@Test
fun main() {
    val smtpBackend = SmtpBackend(
        mailAddress = "",
        password = "",
        server = "",
        encryptMethod = "",
        portNumber = 465
    )
    println(smtpBackend.verify())
}