package com.arctouch.codechallenge.data.model

object Cache {

    var genres = listOf<Genre>()

    fun cacheGenres(genres: List<Genre>) {
        Cache.genres = genres
    }
}
