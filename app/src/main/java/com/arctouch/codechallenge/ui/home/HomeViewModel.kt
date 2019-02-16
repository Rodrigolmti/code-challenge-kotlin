package com.arctouch.codechallenge.ui.home

import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.data.model.Movie
import com.arctouch.codechallenge.data.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.data.repository.IRepository
import com.arctouch.codechallenge.ui.base.BaseViewModel
import com.arctouch.codechallenge.util.isDeviceHaveNoConnection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: IRepository) : BaseViewModel() {

    val movies = MutableLiveData<List<Movie>>()

    init {
        getUpcomingMovies()
    }

    fun getUpcomingMovies() {

        if (isDeviceHaveNoConnection()) {
            showToast(R.string.app_generic_no_connection_error)
            error.set(true)
            return
        }

        compositeDisposable.add(
            repository.upcomingMovies(1)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { loading.set(true) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleResponse(it)
                    loading.set(false)
                }, {
                    loading.set(false)
                    error.set(true)
                })
        )
    }

    private fun handleResponse(response: UpcomingMoviesResponse) {
        response.takeIf { it.results.isNotEmpty() }?.let {
            movies.value = it.results
            error.set(false)
        } ?: run { error.set(true) }
    }
}