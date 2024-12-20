package org.subnull.bookshelfs.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.subnull.bookshelfs.source.BookApiSource
import retrofit2.Retrofit

interface AppContainer {
    val bookRepository: BookRepository
}

class DefaultAppContainer: AppContainer {

    private val baseUrl = "https://www.googleapis.com/books/v1/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: BookApiSource by lazy {
        retrofit.create(BookApiSource::class.java)
    }
    override val bookRepository: BookRepository by lazy {
         NetworkBookRepository(retrofitService)
    }
}