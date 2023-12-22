package com.example.bookreader.data.repository

import com.example.bookreader.common.Resource
import com.example.bookreader.data.remote.BooksApi
import com.example.bookreader.data.remote.responses.User
import com.example.bookreader.domain.models.UserAuth
import com.example.bookreader.domain.models.UserReg
import com.example.bookreader.domain.repository.UserRepository
import retrofit2.Response

class UserRepositoryImpl(
    private val api: BooksApi
) : UserRepository {
    override suspend fun registration(user: UserReg): Response<User> {
        return api.registration(user)
    }

    override suspend fun login(user: UserAuth): Response<User> {
        return api.login(user)
    }

    override suspend fun getUser(userId: Int): Resource<User> {
        val response = try {
            api.getUser(userId)
        } catch (e: Exception) {
            return Resource.Error("Неизвестная ошибка подключения к локальной базе данных.")
        }
        return Resource.Success(response)
    }

}