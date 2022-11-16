package com.hnu.nanamail.dao

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hnu.nanamail.data.Contact
import com.hnu.nanamail.data.Mail
import com.hnu.nanamail.data.User

@Database(
    entities = [User::class, Mail::class, Contact::class],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 2, to = 3)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun mailDao(): MailDao
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).addMigrations().build()
                INSTANCE = instance
                instance
            }
        }
    }
}