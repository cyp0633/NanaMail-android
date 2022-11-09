package com.hnu.nanamail.data

class Pop3Backend(
    var mailAddress: String,
    var password: String,
    var server: String,
    var encryptMethod: String,
    var portNumber: Int
) {
    fun verify(): String {
        return "success"
    }
}