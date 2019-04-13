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

    private val movies: MutableList<Movie> = mutableListOf()
    val moviesLiveData = MutableLiveData<List<Movie>>()

    private val page = Page()

    init {
        getUpcomingMovies()
    }

    override fun detachView() {
        super.detachView()
        page.resetPages()
    }

    fun getUpcomingMovies() {

        if (isDeviceHaveNoConnection()) {
            error.set(true)
            return
        }

        if (page.isLastPage()) {
            return
        }

        page.nextPage()

        compositeDisposable.add(
            repository.getUpcomingMovies(page.currentPage)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { loading.set(true) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleResponse(it)
                    loading.set(false)
                }, {
                    showToast(R.string.app_generic_connection_error)
                    loading.set(false)
                    error.set(true)
                })
        )
    }

    fun searchMovies(query: String) {
        moviesLiveData.value = movies.filter {
            it.title.toLowerCase().contains(query.toLowerCase())
        }.toList()
    }

    private fun handleResponse(response: UpcomingMoviesResponse) {
        response.takeIf { it.results.isNotEmpty() }?.let {
            page.lastPage = response.totalPages
            movies.addAll(it.results)
            moviesLiveData.value = movies
            error.set(false)
        } ?: run {
            error.set(true)
        }
    }

    class Page {

        var currentPage: Long = 0
        var lastPage: Long = 1

        fun isLastPage(): Boolean = currentPage == lastPage
        fun nextPage() = currentPage++
        fun resetPages() {
            currentPage = 0
            lastPage = 1
        }
    }
}