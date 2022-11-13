package com.hnu.nanamail.data

import org.junit.Test

@Test
suspend fun main() {
    val pop3Backend = Pop3Backend
    pop3Backend.mailAddress = "test@testmail"
    pop3Backend.password = "test"
    pop3Backend.server = "10.0.0.192"
    pop3Backend.encryptMethod = ""
    pop3Backend.portNumber = 3110
    println(pop3Backend.verify())
}