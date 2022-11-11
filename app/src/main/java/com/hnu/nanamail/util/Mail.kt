package com.hnu.nanamail.util

import javax.mail.Flags
import javax.mail.Message
import javax.mail.Multipart
import javax.mail.Part
import javax.mail.internet.InternetAddress

// 是否已读
fun isRead(message: Message): Boolean {
    val flags = message.flags.systemFlags
    for (f in flags) {
        if (f == Flags.Flag.SEEN) return true
    }
    return false
}

// 是否含有附件（Copilot 补全，我也不知道对不对）
fun hasAttachment(message: Message): Boolean {
    val contentType = message.contentType
    if (contentType.contains("multipart")) {
        val multipart = message.content as Multipart
        for (i in 0 until multipart.count) {
            val bodyPart = multipart.getBodyPart(i)
            if (Part.ATTACHMENT.equals(bodyPart.disposition, ignoreCase = true)) {
                return true
            }
        }
    }
    return false
}

// 解析邮件内容
fun parseContent(message: Message): String {
    val contentType = message.contentType
    if (contentType.contains("multipart")) {
        val multipart = message.content as Multipart
        for (i in 0 until multipart.count) {
            val bodyPart = multipart.getBodyPart(i)
            if (Part.ATTACHMENT.equals(bodyPart.disposition, ignoreCase = true)) {
                continue
            }
            return bodyPart.content.toString()
        }
    }
    return message.content.toString()
}

// 解析收件人
fun parseRecipientTo(message: Message): String {
    val recipients = message.getRecipients(Message.RecipientType.TO)
    val recipient = StringBuilder()
    for (i in recipients.indices) {
        recipient.append((recipients[i] as InternetAddress).address)
        if (i != recipients.size - 1) {
            recipient.append(";")
        }
    }
    return recipient.toString()
}

// 解析抄送人
fun parseRecipientCc(message: Message): String {
    val recipients = message.getRecipients(Message.RecipientType.CC)
    val recipient = StringBuilder()
    for (i in recipients.indices) {
        recipient.append((recipients[i] as InternetAddress).address)
        if (i != recipients.size - 1) {
            recipient.append(";")
        }
    }
    return recipient.toString()
}
