package com.example.bookreader.domain.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bookreader.common.Resource
import com.example.bookreader.data.locale.UserInfo

interface UserInfoRepository {


    suspend fun saveUser(user: UserInfo)

    suspend fun deleteUser(user: UserInfo)

    suspend fun getUser(): Resource<UserInfo>
}