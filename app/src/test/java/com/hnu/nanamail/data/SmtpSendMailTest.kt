package com.hnu.nanamail.data

fun main() {
    SmtpBackend.mailAddress = "test@testmail"
    SmtpBackend.password = "test"
    SmtpBackend.server = "code.internal.cyp0633.icu"
//    SmtpBackend.encryptMethod = "TLS"
    SmtpBackend.portNumber = 3025
    SmtpBackend.verify()
    SmtpBackend.sendMail(
        "test@testmail",
        "test2@testmail",
        "",
        "",
        "测试标题",
        "测试内容"
    )
}
