package com.hnu.nanamail.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}