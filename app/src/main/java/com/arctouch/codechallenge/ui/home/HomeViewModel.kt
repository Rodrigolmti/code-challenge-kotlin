package com.arctouch.codechallenge.ui.home

import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.data.model.Movie
import com.arctouch.codechallenge.data.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.data.repository.IRepository
import com.arctouch.codechallenge.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: IRepository) : BaseViewModel() {

    val movies = MutableLiveData<List<Movie>>()

    init {
        getUpcomingMovies()
    }

    fun getUpcomingMovies() {

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