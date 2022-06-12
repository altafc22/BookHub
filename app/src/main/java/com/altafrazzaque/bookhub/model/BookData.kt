package com.altafrazzaque.bookhub.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_data")
data class BookData(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val bookSmallThumbnail: String?,
    val title: String,
    val author: String?,
    val publisher: String?,
    val description: String?,
    val previewLink: String?,
    val bookThumbnail: String?,
    var isFavourite: Boolean? = false
)