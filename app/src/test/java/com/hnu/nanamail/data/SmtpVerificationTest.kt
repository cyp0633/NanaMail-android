package com.hnu.nanamail.data

import org.junit.Test


@Test
suspend fun main() {
    val smtpBackend = SmtpBackend
    smtpBackend.mailAddress = "test@testmail"
    smtpBackend.password = "test"
    smtpBackend.server = "10.0.0.192"
    smtpBackend.encryptMethod = ""
    smtpBackend.portNumber = 3025
    println(smtpBackend.verify())
}
