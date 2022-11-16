package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.Contact

/**
 * 联系人视图模型
 * @param application 应用
 * @property contactList 联系人列表
 */
class ContactsViewModel(application: Application):AndroidViewModel(application) {
    var contactList: LiveData<List<Contact>> = MutableLiveData(listOf<Contact>())

    /**
     * 获取联系人列表
     */
    fun getContactList() {
        val db = AppDatabase.getDatabase(getApplication())
        contactList = db.contactDao().getContactList()
    }
}