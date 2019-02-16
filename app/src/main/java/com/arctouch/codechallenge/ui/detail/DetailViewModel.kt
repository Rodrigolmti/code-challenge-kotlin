package com.arctouch.codechallenge.ui.detail

import androidx.databinding.ObservableField
import com.arctouch.codechallenge.App
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.data.model.Movie
import com.arctouch.codechallenge.ui.base.BaseViewModel

class DetailViewModel : BaseViewModel() {

    val bannerUrl = ObservableField<String>()
    val posterUrl = ObservableField<String>()
    val title = ObservableField<String>()
    val overview = ObservableField<String>()
    val voteAverage = ObservableField<String>()

    val releaseDate = ObservableField<String>()
    val language = ObservableField<String>()
    val popularity = ObservableField<String>()

    fun setMovie(movie: Movie) {

        releaseDate.set(
            App.instance.getString(R.string.fragment_detail_open_trailer_release_date, movie.releaseDate)
        )
        language.set(
            App.instance.getString(
                R.string.fragment_detail_open_trailer_release_language,
                movie.originalLanguage?.capitalize()
            )
        )
        popularity.set(
            App.instance.getString(
                R.string.fragment_detail_open_trailer_release_popularity,
                movie.popularity
            )
        )

        voteAverage.set(movie.voteAverage.toString())
        bannerUrl.set(movie.backdropPath)
        posterUrl.set(movie.posterPath)
        overview.set(movie.overview)
        title.set(movie.title)
    }
}