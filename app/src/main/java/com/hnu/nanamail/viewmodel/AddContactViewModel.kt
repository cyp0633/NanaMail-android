package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * 添加联系人视图模型
 * @property name 联系人姓名
 * @property email 联系人邮箱
 * @property description 联系人描述
 */
class AddContactViewModel(application: Application) : AndroidViewModel(application) {
    val name = mutableStateOf("")
    val email = mutableStateOf("")
    val description = mutableStateOf("")

    /**
     * 添加联系人
     */
    fun submit() = viewModelScope.launch(Dispatchers.IO) {
        val db = AppDatabase.getDatabase(getApplication())
        db.contactDao().insertContacts(
            Contact(
                uuid = UUID.randomUUID().toString(),
                name = name.value,
                address = email.value,
                description = description.value
            )
        )
    }
}