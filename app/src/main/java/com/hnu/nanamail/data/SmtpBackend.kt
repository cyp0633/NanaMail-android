package com.hnu.nanamail.data

class SmtpBackend(
    var mailAddress: String,
    var password: String,
    var server: String,
    var encryptMethod: String,
    var portNumber: String
) {
    fun verify(): String {
        return "success"
    }
}