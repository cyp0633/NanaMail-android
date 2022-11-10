package com.hnu.nanamail.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hnu.nanamail.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): User?

    @Delete
    fun deleteUser(user: User)

    @Insert
    fun insertUser(user: User)
}