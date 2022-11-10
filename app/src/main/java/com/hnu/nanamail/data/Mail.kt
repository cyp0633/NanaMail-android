package com.hnu.nanamail.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Mail(
    // 邮件 UUID
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    val uuid: String,
    // 所属邮箱账户
    @ColumnInfo(name = "account")
    val account: String,
    // 发送方名字，如 cyp0633
    @ColumnInfo(name = "sender")
    val sender: String,
    // 发送邮件地址，如 cyp0633@cyp0633.icu
    @ColumnInfo(name = "sender_address")
    val senderAddress: String,
    // 收件人邮箱，可能有多个
    @ColumnInfo(name = "receive_to")
    val receiveTo: String,
    // 抄送人邮箱，可能有多个
    @ColumnInfo(name = "receive_cc")
    val receiveCc: String,
    // 邮件标题
    @ColumnInfo(name = "title")
    val title: String,
    // 邮件内容
    @ColumnInfo(name = "content")
    val content: String,
    // 预览内容，最多 100 字符
    @ColumnInfo(name = "preview")
    val preview: String,
    // 是否已读
    @ColumnInfo(name = "is_read")
    val isRead: Boolean,
    // 是否有附件
    @ColumnInfo(name = "has_attachment")
    val hasAttachment: Boolean,
    // 附件是否已下载
    @ColumnInfo(name = "is_attachment_downloaded")
    val attachmentDownloaded: Boolean,
    // uid
    @ColumnInfo(name = "uid")
    val uid: Int,
    // 类型
    @ColumnInfo(name = "type")
    val type: MailType,
    // 时间
    @ColumnInfo(name = "time")
    val time: Long
)

fun Mail.getTimeStr(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("zh", "CN"))
    return simpleDateFormat.format(Date(time * 1000))
}