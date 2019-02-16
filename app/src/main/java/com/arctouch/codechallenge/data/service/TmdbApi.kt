package com.arctouch.codechallenge.data.service

import com.arctouch.codechallenge.data.model.GenreResponse
import com.arctouch.codechallenge.data.model.Movie
import com.arctouch.codechallenge.data.model.UpcomingMoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Long,
        @Query("region") region: String
    ): Single<UpcomingMoviesResponse>

    @GET("genre/movie/list")
    fun getGenreList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<GenreResponse>
}
