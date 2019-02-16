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

    private val movies : MutableList<Movie> = mutableListOf()
    val moviesLiveData = MutableLiveData<List<Movie>>()

    var currentPage = 0
    var lastPage = 1

    init {
        getUpcomingMovies()
    }

    fun getUpcomingMovies() {

        if (isDeviceHaveNoConnection()) {
            showToast(R.string.app_generic_no_connection_error)
            error.set(true)
            return
        }

        if (currentPage == lastPage) {
            return
        }

        currentPage++

        compositeDisposable.add(
            repository.upcomingMovies(currentPage)
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
            lastPage = response.totalPages
            movies.addAll(it.results)
            moviesLiveData.value = movies
            error.set(false)
        } ?: run { error.set(true) }
    }
}