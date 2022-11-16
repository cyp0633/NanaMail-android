package com.hnu.nanamail.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import com.hnu.nanamail.data.Contact

/**
 * 联系人数据库操作接口
 */
@Dao
interface ContactDao {
    /**
     * 向数据库插入多个联系人
     * @param contacts 联系人列表
     */
    @Insert
    fun insertContacts(vararg contacts: Contact)

    /**
     * 删除联系人
     * @param uuid 联系人 ID 列表
     */
    @androidx.room.Query("DELETE FROM contact WHERE uuid IN (:uuid)")
    fun deleteContacts(vararg uuid: String)

    /**
     * 获取联系人列表
     * @return 联系人列表
     */
    @androidx.room.Query("SELECT * FROM contact")
    fun getContactList(): LiveData<List<Contact>>

    /**
     * 根据 UUID 获取联系人
     * @param uuid 联系人 ID
     */
    @androidx.room.Query("SELECT * FROM contact WHERE uuid = :uuid")
    fun getContact(uuid: String): Contact?
}