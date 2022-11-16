package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hnu.nanamail.dao.AppDatabase
import com.hnu.nanamail.data.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactDetailViewModel(application: Application) : AndroidViewModel(application) {
    val contact = mutableStateOf(Contact())
    val exist = mutableStateOf(true)

    fun fetch(uuid: String) = viewModelScope.launch(Dispatchers.IO) {
        val db = AppDatabase.getDatabase(getApplication())
        val tempContact = db.contactDao().getContact(uuid)
        exist.value = tempContact != null
        contact.value = tempContact ?: Contact()
    }
}