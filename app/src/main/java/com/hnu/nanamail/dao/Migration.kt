package com.hnu.nanamail.dao

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `contact` (`uuid` TEXT PRIMARY KEY, `name` TEXT, `address` TEXT, `description` TEXT)"
        )
    }
}