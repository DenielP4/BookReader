package com.example.bookreader.di

import com.example.bookreader.common.Constants.BASE_URL
import com.example.bookreader.data.remote.BooksApi
import com.example.bookreader.data.repository.BookRepositoryImpl
import com.example.bookreader.data.repository.DeskOfBookRepositoryImpl
import com.example.bookreader.data.repository.ReviewRepositoryImpl
import com.example.bookreader.domain.repository.BookRepository
import com.example.bookreader.domain.repository.DeskOfBookRepository
import com.example.bookreader.domain.repository.ReviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpBuilder = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)

    @Singleton
    @Provides
    fun provideReviewRepository(api: BooksApi) : ReviewRepository {
        return ReviewRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideDeskOfBookRepository(api: BooksApi) : DeskOfBookRepository {
        return DeskOfBookRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideBookRepository(api: BooksApi) : BookRepository {
        return BookRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideBooksApi(): BooksApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpBuilder.build())
            .build()
            .create(BooksApi::class.java)
    }
}