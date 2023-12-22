package com.example.bookreader.domain.repository

import com.example.bookreader.common.Resource
import com.example.bookreader.data.remote.responses.User
import com.example.bookreader.domain.models.UserAuth
import com.example.bookreader.domain.models.UserReg
import retrofit2.Response

interface UserRepository {
    suspend fun registration(user: UserReg): Response<User>
    suspend fun login(user: UserAuth): Response<User>
    suspend fun getUser(userId: Int): Resource<User>

}