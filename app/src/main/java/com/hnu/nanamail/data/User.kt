package com.hnu.nanamail.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class User(
    @PrimaryKey
    @ColumnInfo(name = "mail_address")
    val mailAddress: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "pop3_server")
    val pop3Server: String,
    @ColumnInfo(name = "receive_encrypt_method")
    val receiveEncryptMethod: String,
    @ColumnInfo(name = "receive_port_number")
    val receivePortNumber: String,
    @ColumnInfo(name = "smtp_server")
    val smtpServer: String,
    @ColumnInfo(name = "send_encrypt_method")
    val sendEncryptMethod: String,
    @ColumnInfo(name = "send_port_number")
    val sendPortNumber: String
)
