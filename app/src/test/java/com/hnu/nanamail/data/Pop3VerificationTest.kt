package com.hnu.nanamail.data

import org.junit.Test

@Test
fun main() {
    val pop3Backend = Pop3Backend
    pop3Backend.mailAddress = "test@testmail"
    pop3Backend.password = "test"
    pop3Backend.server = "code.internal.cyp0633.icu"
    pop3Backend.encryptMethod = ""
    pop3Backend.portNumber = 3110
    println(pop3Backend.verify())
}