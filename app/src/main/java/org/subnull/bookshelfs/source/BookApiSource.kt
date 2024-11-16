package org.subnull.bookshelfs.source

import org.subnull.bookshelfs.model.Books
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApiSource {
    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String): Books
//
//    @GET("{id}")
//    suspend fun getBookById(@Path("id") id: String): Book
}