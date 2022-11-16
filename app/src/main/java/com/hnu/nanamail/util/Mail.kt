package com.hnu.nanamail.util

import android.util.Log
import com.hnu.nanamail.data.Mail
import com.hnu.nanamail.data.MailType
import com.hnu.nanamail.data.Pop3Backend
import java.io.IOException
import javax.mail.*
import javax.mail.internet.ContentType
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMultipart


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
        val hasAttachment = hasAttachment(m)
        var content = "test"
        try {
            content = getTextFromMessage(m)
        } catch (e: MessagingException) {
            e.printStackTrace()
            Log.e("MailUtil", "messaging exception: ${e.message}, ${e.cause}")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("MailUtil", "io exception: ${e.message}, ${e.cause}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("MailUtil", "exception: ${e.message}, ${e.cause}")
        }
        // 发送者和其邮箱
        val fromAddress = (m.from[0] as InternetAddress).address
        val from = (m.from[0] as InternetAddress).personal
        val recipientTo = parseRecipientTo(m)
        val recipientCc = parseRecipientCc(m)
        val uid = m.messageNumber
        val subject = m.subject
        val preview = if (content.length > 100) content.substring(0, 100) else content
        val mail = "${uid}+${m.sentDate.time}".md5()?.let { md5 ->
            Mail(
                uuid = md5,
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
        }
        mailList = mailList.plus(mail) as MutableList<Mail>
    }
    return mailList
}

/**
 * 获取邮件文本内容
 *
 * From [Stack Overflow](https://stackoverflow.com/a/36932127)
 *
 * @param message 邮件
 * @return 邮件文本内容
 */
@Throws(IOException::class, MessagingException::class)
private fun getTextFromMessage(message: Message): String {
    var result: String = ""
    if (message.isMimeType("text/plain")) {
        result = message.content.toString()
    } else if (message.isMimeType("multipart/*")) {
        val mimeMultipart = message.content as MimeMultipart
        result = getTextFromMimeMultipart(mimeMultipart)
    }
    return result
}

/**
 * 递归获取邮件文本内容
 *
 * From [Stack Overflow](https://stackoverflow.com/a/36932127)
 *
 * @param mimeMultipart 邮件
 * @return 邮件文本内容
 */
@Throws(IOException::class, MessagingException::class)
private fun getTextFromMimeMultipart(
    mimeMultipart: MimeMultipart
): String {
    val count = mimeMultipart.count
    if (count == 0) throw MessagingException("Multipart with no body parts not supported.")
    val multipartAlt: Boolean =
        ContentType(mimeMultipart.contentType).match("multipart/alternative")
    if (multipartAlt) // alternatives appear in an order of increasing
    // faithfulness to the original content. Customize as req'd.
        return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1))
    var result: String = ""
    for (i in 0 until count) {
        val bodyPart = mimeMultipart.getBodyPart(i)
        result += getTextFromBodyPart(bodyPart)
    }
    return result
}

/**
 * 递归获取邮件文本内容
 *
 * From [Stack Overflow](https://stackoverflow.com/a/36932127)
 *
 * @param bodyPart 邮件
 * @return 邮件文本内容
 */
@Throws(IOException::class, MessagingException::class)
private fun getTextFromBodyPart(
    bodyPart: BodyPart
): String {
    var result = ""
    if (bodyPart.isMimeType("text/plain")) {
        result = bodyPart.content as String
    } else if (bodyPart.isMimeType("text/html")) {
        val html = bodyPart.content as String
        result = org.jsoup.Jsoup.parse(html).text()
    } else if (bodyPart.content is MimeMultipart) {
        result = getTextFromMimeMultipart(bodyPart.content as MimeMultipart)
    }
    return result
}