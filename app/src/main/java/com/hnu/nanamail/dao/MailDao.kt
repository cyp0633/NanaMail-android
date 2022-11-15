package com.hnu.nanamail.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hnu.nanamail.data.Mail
import com.hnu.nanamail.data.MailType

@Dao
interface MailDao {
    /**
     * 向数据库插入多封邮件
     * @param mails 邮件列表
     */
    @Insert
    fun insertMails(vararg mails: Mail)

    /**
     * 向数据库插入一封邮件
     * @param mail 要插入的邮件
     */
    @Insert
    fun insertMail(mail: Mail)

    /**
     * 删除数据库中的多封邮件
     * @param mails 邮件列表
     */
    @Delete
    fun deleteMails(vararg mails: Mail)

    /**
     * 由 UUID 获取一封邮件
     * @param uuid 邮件 UUID
     * @return 单个邮件
     */
    @Query("SELECT * FROM mail WHERE uuid = :uuid")
    fun getMail(uuid: String): Mail?

    /**
     * 由页码和邮件类型获取邮件列表
     * @param page 页码
     * @param type 邮件类型
     * @return 邮件列表
     */
    @Query("SELECT * FROM mail WHERE type = :type ORDER BY time DESC LIMIT :page, 10")
    fun getMailListByPage(page: Int, type: MailType): List<Mail>

    /**
     * 由页码和邮件类型获取邮件列表，LiveData
     * @param page 页码
     * @param type 邮件类型
     * @return 邮件列表，LiveData
     */
    @Query("SELECT * FROM mail WHERE type = :type ORDER BY time DESC LIMIT :page, 10")
    fun getMailListLiveDataByPage(page: Int, type: MailType): LiveData<List<Mail>>

    /**
     * 将特定 uuid 的邮件标记读取状态
     * @param uuid 邮件 UUID
     * @param isRead 是否已读，默认为是
     */
    @Query("UPDATE mail SET is_read = :isRead WHERE uuid = :uuid")
    fun markAsRead(uuid: String, isRead: Boolean = true)

    /**
     * 清空所有邮件
     */
    @Query("DELETE FROM mail")
    fun clearAll()

    /**
     * 移入已发送（一般见于发件箱）
     */
    @Query("UPDATE mail SET type = :type WHERE uuid = :uuid")
    fun moveToSent(uuid: String, type: MailType)
}