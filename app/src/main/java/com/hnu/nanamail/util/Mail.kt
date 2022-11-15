package com.hnu.nanamail.util

import android.util.Log
import com.hnu.nanamail.data.Mail
import com.hnu.nanamail.data.MailType
import com.hnu.nanamail.data.Pop3Backend
import kotlinx.coroutines.flow.flow
import java.util.*
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
    try {
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
    } catch (e: Exception) {
        Log.e("MailUtil", "exception: ${e.message}, ${e.cause}")
        e.printStackTrace()
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
    val recipients = message.getRecipients(Message.RecipientType.CC) ?: return "" // 可能没有抄送人
    val recipient = StringBuilder()
    for (i in recipients.indices) {
        recipient.append((recipients[i] as InternetAddress).address)
        if (i != recipients.size - 1) {
            recipient.append(";")
        }
    }
    return recipient.toString()
}

fun parseMessagesIntoMails(msg: Array<Message>, mailType: MailType): List<Mail> {
    var mailList = mutableListOf<Mail>()
    for (m in msg) {
        val isRead = isRead(m)
//        val hasAttachment = hasAttachment(m)
        val hasAttachment = false
//        val content = parseContent(m)
        val content = "test"
        // TODO: 解析图片
        // 发送者和其邮箱
        val fromAddress = (m.from[0] as InternetAddress).address
        val from = (m.from[0] as InternetAddress).personal
        val recipientTo = parseRecipientTo(m)
        val recipientCc = parseRecipientCc(m)
        val uid = m.messageNumber
        val subject = m.subject
        val preview = if (content.length > 100) content.substring(0, 100) else content
        val mail = Mail(
            uuid = UUID.randomUUID().toString(),
            account = Pop3Backend.mailAddress,
            sender = from,
            senderAddress = fromAddress,
            recipientTo = recipientTo,
            recipientCc = recipientCc,
            subject = subject,
            content = content,
            preview = preview,
            isRead = isRead,
            hasAttachment = hasAttachment,
            attachmentDownloaded = false,
            uid = uid,
            type = mailType,
            time = m.sentDate.time
        )
        mailList = mailList.plus(mail) as MutableList<Mail>
    }
    return mailList
}