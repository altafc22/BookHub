package com.altafrazzaque.bookhub.ui.bookmark

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.altafrazzaque.bookhub.api.NetworkService
import com.altafrazzaque.bookhub.base.BaseViewModel
import com.altafrazzaque.bookhub.database.BookDatabase
import com.altafrazzaque.bookhub.model.*
import com.altafrazzaque.bookhub.model.api.Books
import com.altafrazzaque.bookhub.model.api.VolumeInfo
import com.altafrazzaque.bookhub.repository.BookHubRepository
import com.altafrazzaque.bookhub.utilities.OrderBy
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class BookmarkViewModel : BaseViewModel {
    constructor(application: Application) : super(application) {
        repository = BookHubRepository(application,networkService)
    }
    constructor(application: Application, repository: BookHubRepository) : super(application) {
        this.repository = repository
    }

    //region definition
    private var networkService = NetworkService().api
    private val repository: BookHubRepository

    fun getAllBookmarks() : LiveData<List<BookData>> {
       return repository.readAllData
    }

    fun addBook(book: BookData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBook(book)
        }
        getAllBookmarks()
    }

    fun deleteBook(book: BookData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBook(book)
        }
        getAllBookmarks()
    }
}
