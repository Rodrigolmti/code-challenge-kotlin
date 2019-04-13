package com.arctouch.codechallenge.ui.base

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.App
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

interface IViewModelCycle {

    fun attachView()

    fun detachView()
}

open class BaseViewModel : ViewModel(), IViewModelCycle {

    protected val compositeDisposable = CompositeDisposable()
    val loading = ObservableBoolean(false)
    val error = ObservableBoolean(false)

    protected fun showToast(@StringRes id: Int) {
        Toast.makeText(App.instance, id, Toast.LENGTH_SHORT).show()
    }

    override fun attachView() {
        Timber.i("ViewModel ${this.javaClass} has attached to the view!")
    }

    override fun detachView() {
        Timber.i("ViewModel ${this.javaClass} has detach from the view!")
        compositeDisposable.dispose()
    }
}