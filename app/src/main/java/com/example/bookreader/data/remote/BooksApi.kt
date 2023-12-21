package com.example.bookreader.data.remote

import com.example.bookreader.data.remote.responses.Book
import com.example.bookreader.data.remote.responses.Review
import com.example.bookreader.data.remote.responses.User
import com.example.bookreader.domain.models.UserAuth
import com.example.bookreader.domain.models.UserReg
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BooksApi {

    @POST("book/getAll")
    suspend fun getBookList() : List<Book>

    @POST("book/{id}")
    suspend fun getBookInfo(
        @Path("id") id: Int
    ) : Book

    @POST("user/registration")
    suspend fun registration(
        @Body user: UserReg
    ) : Response<User>

    @POST("user/login")
    suspend fun login(
        @Body user: UserAuth
    ) : Response<User>

    @POST("book/addReview/{id}")
    suspend fun addReview(
        @Path("id") id: Int,
        @Body review: Review
    ) : Review

    @GET("desk/user_book")
    suspend fun getUserBooks(): List<Book>

    @POST("desk/addBook/{id}")
    suspend fun addBookToDesk(
        @Path("id") deskId: Int,
        @Body book: Book
    )

    @DELETE("desk/{id}")
    suspend fun deleteBookFromDesk(
        @Path("id") id: Int
    )

    @DELETE("review/{id}")
    suspend fun deleteReview(
        @Path("id") id: Int
    )
}