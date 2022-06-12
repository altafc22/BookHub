package com.altafrazzaque.bookhub.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.altafrazzaque.bookhub.utilities.OrderBy
import com.altafrazzaque.bookhub.base.BaseViewModel
import com.altafrazzaque.bookhub.api.NetworkService
import com.altafrazzaque.bookhub.model.*
import com.altafrazzaque.bookhub.model.api.Books
import com.altafrazzaque.bookhub.repository.BookHubRepository
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : BaseViewModel {
    constructor(application: Application) : super(application) {
        repository = BookHubRepository(application,networkService)
    }
    constructor(application: Application, repository: BookHubRepository) : super(application) {
        this.repository = repository
    }

    //region definition
    private var networkService = NetworkService().api
    private val repository: BookHubRepository

    private val _isSplashLoading = MutableStateFlow(true)
    val isSplashLoading = _isSplashLoading.asStateFlow()

    private val _isLoading = MutableLiveData<Boolean?>()
    val isLoading: LiveData<Boolean?> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _categoryChips = MutableLiveData<List<Category>>()
    val categoryChips : LiveData<List<Category>> = _categoryChips

    private val _travelBooks = MutableLiveData<Books?>()
    val travelBooks: LiveData<Books?> = _travelBooks

    private val _businessBooks = MutableLiveData<Books?>()
    val businessBooks: LiveData<Books?> = _businessBooks

    private val _educationBooks = MutableLiveData<Books?>()
    val educationBooks: LiveData<Books?> = _educationBooks

    private val _comicBooks = MutableLiveData<Books?>()
    val comicBooks: LiveData<Books?> = _comicBooks

    private val _latestBooks = MutableLiveData<Books?>()
    val latestBooks: LiveData<Books?> = _latestBooks

    var selectedCategory : Category? = null

    //endregion
    init {
        viewModelScope.launch {
            delay(1500)
            _isSplashLoading.value = false
        }
    }

    fun getLatestBooks(category: Category) {
        _isLoading.postValue(true)
        val disposable = repository.getCategories(category.query, OrderBy.RELEVANCE,40)
            .subscribeWith(object : DisposableSingleObserver<Books>() {
                override fun onSuccess(t: Books) {
                    _isLoading.postValue(false)
                    _errorMessage.postValue("")
                    t.let {
                        _latestBooks.postValue(it)
                    }
                }
                override fun onError(e: Throwable) {
                    Timber.e(e.message)
                    _isLoading.postValue(false)
                    _errorMessage.postValue(e.message)
                }
            })
        addDisposable(disposable)
    }
    fun getBooks(category: Category) {
        _isLoading.postValue(true)
        val disposable = repository.getCategories(category.query, OrderBy.RELEVANCE,20)
            .subscribeWith(object : DisposableSingleObserver<Books>() {
                override fun onSuccess(t: Books) {
                    _isLoading.postValue(false)
                    _errorMessage.postValue("")
                    Timber.i("data received ${t.items.size}")
                    t.let {
                        _latestBooks.postValue(it)
                        Timber.i("ITeMS IN LIST: ${latestBooks.value?.items?.size}")
                    }
                }

                override fun onError(e: Throwable) {
                    Timber.e(e.message)
                    _isLoading.postValue(false)
                    _errorMessage.postValue(e.message)
                }

            })
        addDisposable(disposable)
    }

    fun getTravelBooks() {
        _isLoading.postValue(true)
        val category = categoryTravel()
        val disposable = repository.getCategories(category.query, OrderBy.RELEVANCE,20)
            .subscribeWith(object : DisposableSingleObserver<Books>() {
                override fun onSuccess(t: Books) {
                    _isLoading.postValue(false)
                    _errorMessage.postValue("")
                    t.let {
                        _travelBooks.postValue(it)
                    }
                }
                override fun onError(e: Throwable) {
                    Timber.e(e.message)
                    _isLoading.postValue(false)
                    _errorMessage.postValue(e.message)
                }
            })
        addDisposable(disposable)
    }
    fun getBusinessBooks() {
        _isLoading.postValue(true)
        val category = categoryBusiness()
        val disposable = repository.getCategories(category.query, OrderBy.RELEVANCE,20)
            .subscribeWith(object : DisposableSingleObserver<Books>() {
                override fun onSuccess(t: Books) {
                    _isLoading.postValue(false)
                    _errorMessage.postValue("")
                    t.let {
                        _businessBooks.postValue(it)
                    }
                }
                override fun onError(e: Throwable) {
                    Timber.e(e.message)
                    _isLoading.postValue(false)
                    _errorMessage.postValue(e.message)
                }
            })
        addDisposable(disposable)
    }
    fun getEducationBooks() {
        _isLoading.postValue(true)
        val category = categoryEducation()
        val disposable = repository.getCategories(category.query, OrderBy.RELEVANCE,20)
            .subscribeWith(object : DisposableSingleObserver<Books>() {
                override fun onSuccess(t: Books) {
                    _isLoading.postValue(false)
                    _errorMessage.postValue("")
                    t.let {
                        _educationBooks.postValue(it)
                    }
                }
                override fun onError(e: Throwable) {
                    Timber.e(e.message)
                    _isLoading.postValue(false)
                    _errorMessage.postValue(e.message)
                }
            })
        addDisposable(disposable)
    }
    fun getComicBooks() {
        _isLoading.postValue(true)
        val category = categoryComic()
        val disposable = repository.getCategories(category.query, OrderBy.RELEVANCE,40)
            .subscribeWith(object : DisposableSingleObserver<Books>() {
                override fun onSuccess(t: Books) {
                    _isLoading.postValue(false)
                    _errorMessage.postValue("")
                    t.let {
                        _comicBooks.postValue(it)
                    }
                }
                override fun onError(e: Throwable) {
                    Timber.e(e.message)
                    _isLoading.postValue(false)
                    _errorMessage.postValue(e.message)
                }
            })
        addDisposable(disposable)
    }

    fun getCategoryChips() {
        Timber.i("get Category Chips")
        _categoryChips.postValue(categoryList)
    }
}
