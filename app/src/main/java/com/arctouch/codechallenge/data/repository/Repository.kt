package com.arctouch.codechallenge.data.repository

import com.arctouch.codechallenge.data.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.data.service.TmdbApi
import com.arctouch.codechallenge.util.*
import io.reactivex.Single
import java.util.*

interface IRepository {

    fun upcomingMovies(page: Int): Single<UpcomingMoviesResponse>
}

class Repository(private val tmdbApi: TmdbApi) : IRepository {

    private val defaultLanguage = Locale.getDefault().displayLanguage
    private val defaultRegion = Locale.getDefault().country

    override fun upcomingMovies(page: Int): Single<UpcomingMoviesResponse> {

        return tmdbApi.upcomingMovies(BASE_KEY, defaultLanguage, page, defaultRegion).map {
            it.results.map { movie ->
                movie.backdropPath = BACKDROP_URL + movie.backdropPath
                movie.posterPath = POSTER_URL + movie.posterPath
            }
            it
        }
    }
}