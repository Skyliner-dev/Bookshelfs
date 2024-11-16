package org.subnull.bookshelfs.data

import org.subnull.bookshelfs.model.Book
import org.subnull.bookshelfs.model.Books
import org.subnull.bookshelfs.source.BookApiSource

interface BookRepository {
    suspend fun getBooks(q:String): Books
//    suspend fun getBookById(id:String): Book
}

class NetworkBookRepository(
    private val bookSource: BookApiSource
) : BookRepository {
    override suspend fun getBooks(q: String): Books = bookSource.getBooks(query = q)
//    override suspend fun getBookById(id: String): Book = bookSource.getBookById(id)
}