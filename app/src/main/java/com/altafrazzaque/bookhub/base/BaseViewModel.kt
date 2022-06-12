package com.altafrazzaque.bookhub.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel(application: Application) :  AndroidViewModel(application) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    internal fun addDisposable(d: Disposable) {
        disposable.add(d)
    }

    override fun onCleared() {
        disposable.dispose()
        disposable.clear()
        super.onCleared()
    }
}