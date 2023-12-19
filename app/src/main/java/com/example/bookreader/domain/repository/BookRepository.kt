package com.example.bookreader.domain.repository

import com.example.bookreader.common.Resource
import com.example.bookreader.data.remote.responses.Book

interface BookRepository {
    suspend fun getBookList() : Resource<List<Book>>
    suspend fun getBookInfo(bookId: Int) : Resource<Book>
}