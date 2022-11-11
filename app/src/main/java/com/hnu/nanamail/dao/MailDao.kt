package com.hnu.nanamail.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hnu.nanamail.data.Mail

@Dao
interface MailDao {
    // 向数据库插入多封邮件
    @Insert
    fun insertMails(vararg mails: List<Mail>)

    // 删除数据库中的多封邮件
    @Delete
    fun deleteMails(vararg mails: Mail)

    // 由 UUID 获取一封邮件
    @Query("SELECT * FROM mail WHERE uuid = :uuid")
    fun getMail(uuid: String): Mail?

    // 分页获取邮件，每页 10 封
    @Query("SELECT * FROM mail ORDER BY time DESC LIMIT :page, 10")
    fun getMailListByPage(page: Int): List<Mail>
}