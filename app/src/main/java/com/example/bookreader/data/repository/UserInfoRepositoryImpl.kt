package com.example.bookreader.data.repository

import com.example.bookreader.common.Resource
import com.example.bookreader.data.locale.BookDao
import com.example.bookreader.data.locale.UserInfo
import com.example.bookreader.domain.repository.UserInfoRepository

class UserInfoRepositoryImpl(
    private val dao: BookDao
) : UserInfoRepository {
    override suspend fun saveUser(user: UserInfo) {
        dao.saveUser(user)
    }

    override suspend fun deleteUser(user: UserInfo) {
        dao.deleteUser(user)
    }

    override suspend fun getUser(): Resource<UserInfo> {
        val response = try {
            dao.getUser()
        } catch (e: Exception) {
            return Resource.Error("Неизвестная ошибка подключения к локальной базе данных.")
        }
        return Resource.Success(response)
    }
}