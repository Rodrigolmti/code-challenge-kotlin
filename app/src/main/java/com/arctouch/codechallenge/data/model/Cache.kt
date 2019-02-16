package com.arctouch.codechallenge.data.model

import com.arctouch.codechallenge.ui.model.Genre

object Cache {

    var genres = listOf<Genre>()

    fun cacheGenres(genres: List<Genre>) {
        Cache.genres = genres
    }
}
