package com.arctouch.codechallenge.data.repository

import com.arctouch.codechallenge.data.model.GenreResponse
import com.arctouch.codechallenge.data.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.data.service.TmdbApi
import com.arctouch.codechallenge.util.BACKDROP_URL
import com.arctouch.codechallenge.util.BASE_KEY
import com.arctouch.codechallenge.util.POSTER_URL
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.*

interface IRepository {

    fun getUpcomingMovies(page: Long): Single<UpcomingMoviesResponse>
}

class Repository(private val tmdbApi: TmdbApi) : IRepository {

    private val defaultLanguage = Locale.getDefault().language
    private val defaultRegion = Locale.getDefault().country

    override fun getUpcomingMovies(page: Long): Single<UpcomingMoviesResponse> {
        return Single.zip(tmdbApi.getUpcomingMovies(BASE_KEY, defaultLanguage, page, defaultRegion),
            tmdbApi.getGenreList(BASE_KEY, defaultLanguage),
            BiFunction<UpcomingMoviesResponse, GenreResponse, UpcomingMoviesResponse> { movies, genres ->
                movies.results.map { movie ->

                    movie.backdropPath = BACKDROP_URL + movie.backdropPath
                    movie.posterPath = POSTER_URL + movie.posterPath

                    movie.genres = genres.genres.asSequence()
                        .filter { genre ->
                            movie.genreIds?.contains(genre.id) == true
                        }.map { it.name }
                        .toList()
                        .joinToString(separator = ", ")
                }
                movies
            })
    }
}