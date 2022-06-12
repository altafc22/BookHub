package com.altafrazzaque.bookhub.repository

import android.app.Application
import android.icu.text.IDNA.Info
import androidx.lifecycle.LiveData
import com.altafrazzaque.bookhub.api.ApiService
import com.altafrazzaque.bookhub.database.BookDao
import com.altafrazzaque.bookhub.database.BookDatabase
import com.altafrazzaque.bookhub.model.BookData
import com.altafrazzaque.bookhub.model.Category
import com.altafrazzaque.bookhub.model.api.Books
import com.altafrazzaque.bookhub.model.api.Item
import com.altafrazzaque.bookhub.model.api.VolumeInfo
import com.altafrazzaque.bookhub.model.categoryList
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class BookHubRepository(application: Application, private val apiService: ApiService) {

    lateinit var bookDao : BookDao
    init {
        bookDao = BookDatabase.getDatabase(application).bookDao()
    }


    fun getCategories(query:String, orderBy:String?,maxResult:Int?): Single<Books> {

        return apiService.getCategories(query,orderBy,maxResult)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    val readAllData: LiveData<List<BookData>> = bookDao.readAllData()

    suspend fun addBook(book: BookData) {
        bookDao.addBook(book)
    }

    suspend fun deleteBook(book: BookData) {
        bookDao.deleteBook(book)
    }
}