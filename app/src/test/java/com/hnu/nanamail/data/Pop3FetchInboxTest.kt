package com.hnu.nanamail.data

import org.junit.Test

@Test
suspend fun main() {
    Pop3Backend.mailAddress = "test2@testmail"
    Pop3Backend.password = "test2"
    Pop3Backend.server = "code.internal.cyp0633.icu"
    Pop3Backend.encryptMethod = ""
    Pop3Backend.portNumber = 3110
    Pop3Backend.verify()
    val result = Pop3Backend.fetchInbox()
    println(result)
}