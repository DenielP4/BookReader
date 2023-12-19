package com.example.bookreader.data.repository

import com.example.bookreader.common.Resource
import com.example.bookreader.data.remote.BooksApi
import com.example.bookreader.data.remote.responses.Book
import com.example.bookreader.domain.repository.DeskOfBookRepository

class DeskOfBookRepositoryImpl(
    private val api: BooksApi
) : DeskOfBookRepository {
    override suspend fun getUserBooks(): Resource<List<Book>> {
        val response = try {
            api.getUserBooks()
        } catch (e: Exception) {
            return Resource.Error("Книги пользователя не были получены.")
        }
        return Resource.Success(response)
    }

    override suspend fun addBookToDesk(id: Int, book: Book) {
        api.addBookToDesk(id, book)
    }

    override suspend fun deleteBookFromDesk(id: Int) {
        api.deleteBookFromDesk(id)
    }

}