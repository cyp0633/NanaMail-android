package com.hnu.nanamail.data

import org.junit.Test


@Test
fun main() {
    val smtpBackend = SmtpBackend
    smtpBackend.mailAddress = "test@testmail"
    smtpBackend.password = "test"
    smtpBackend.server = "code.internal.cyp0633.icu"
    smtpBackend.encryptMethod = ""
    smtpBackend.portNumber = 3025
    println(smtpBackend.verify())
}
