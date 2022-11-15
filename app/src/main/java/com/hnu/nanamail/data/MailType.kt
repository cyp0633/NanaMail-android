package com.hnu.nanamail.data

enum class MailType {
    // 收件箱
    INBOX,
    // 已发送
    SENT,
    // 草稿箱
    DRAFT,
    // 垃圾邮件
    TRASH,
    // 发送中
    OUTBOX,
    // 已删除
    DELETED
}