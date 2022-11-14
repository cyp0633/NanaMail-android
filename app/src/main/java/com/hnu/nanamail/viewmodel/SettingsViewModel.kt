package com.hnu.nanamail.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hnu.nanamail.dao.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application):AndroidViewModel(application) {
    fun clearData() {
        viewModelScope.launch (Dispatchers.IO)  {
            val db = AppDatabase.getDatabase(getApplication())
        }
    }
}
