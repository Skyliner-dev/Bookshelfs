package org.subnull.bookshelfs.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Books(
    @SerialName("items")
    val items: List<Book>
)

@Serializable
data class Book(
    @SerialName("id")
    val id:String,

    @SerialName("volumeInfo")
    val volumeInfo: Content
)

@Serializable
data class Content(
    val title: String,

    @SerialName("imageLinks")
    val imageLinks: Thumbnails? = null
)

@Serializable
data class Thumbnails(
    @SerialName("smallThumbnail")
    val smallThumbnail: String? = null,

    @SerialName("thumbnail")
    val thumbnail: String
)