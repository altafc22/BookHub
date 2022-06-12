package com.altafrazzaque.bookhub.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.altafrazzaque.bookhub.model.BookData


@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBook(books: BookData)

    @Query("SELECT * FROM book_data ORDER BY id ASC")
    fun readAllData(): LiveData<List<BookData>>

    @Delete
    suspend fun deleteBook(book: BookData)
}