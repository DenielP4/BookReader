package com.example.bookreader.domain.repository

import com.example.bookreader.common.Resource
import com.example.bookreader.data.remote.responses.Book
import com.example.bookreader.data.remote.responses.DeskOfBook
import kotlinx.coroutines.flow.Flow

interface DeskOfBookRepository {
    suspend fun getUserBooks(): Resource<List<Book>>
    suspend fun addBookToDeskById(userId: Int, bookId: Int)
    suspend fun deleteBookFromDesk(id: Int)
}