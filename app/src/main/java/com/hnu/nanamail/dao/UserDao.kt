package com.hnu.nanamail.dao

import androidx.room.Dao
import androidx.room.Query
import com.hnu.nanamail.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): User

}