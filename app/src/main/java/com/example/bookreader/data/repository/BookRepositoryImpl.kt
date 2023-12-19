package com.example.bookreader.data.repository

import com.example.bookreader.common.Resource
import com.example.bookreader.data.remote.BooksApi
import com.example.bookreader.data.remote.responses.Book
import com.example.bookreader.domain.repository.BookRepository

class BookRepositoryImpl(
    private val api: BooksApi
) : BookRepository {
    override suspend fun getBookList(): Resource<List<Book>> {
        val response = try {
            api.getBookList()
        } catch (e: Exception) {
            return Resource.Error("Неизвестная ошибка подключения к серверу.")
        }
        return Resource.Success(response)
    }

    override suspend fun getBookInfo(bookId: Int): Resource<Book> {
        val response = try {
            api.getBookInfo(bookId)
        } catch (e: Exception) {
            return Resource.Error("Информация о книге не найдена.")
        }
        return Resource.Success(response)
    }
}