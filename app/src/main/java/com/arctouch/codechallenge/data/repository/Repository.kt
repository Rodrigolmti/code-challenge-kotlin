package com.arctouch.codechallenge.data.repository

import com.arctouch.codechallenge.data.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.data.service.TmdbApi
import com.arctouch.codechallenge.util.*
import io.reactivex.Single

interface IRepository {

    fun upcomingMovies(page: Int): Single<UpcomingMoviesResponse>
}

class Repository(private val tmdbApi: TmdbApi) : IRepository {

    override fun upcomingMovies(page: Int): Single<UpcomingMoviesResponse> {
        return tmdbApi.upcomingMovies(BASE_KEY, DEFAULT_LANGUAGE, 1, DEFAULT_REGION).map {
            it.results.map { movie ->
                movie.backdropPath = BACKDROP_URL + movie.backdropPath
                movie.posterPath = POSTER_URL + movie.posterPath
            }
            it
        }
    }
}