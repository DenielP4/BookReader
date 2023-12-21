package com.example.bookreader.data.locale

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface BookDao {

    @Insert
    suspend fun saveUser(user: UserInfo)

    @Delete
    suspend fun deleteUser(user: UserInfo)

    @Query("SELECT * FROM user_info_table")
    suspend fun getUser(): UserInfo
}