package com.arctouch.codechallenge.data.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class GenreResponse(val genres: List<Genre>)

data class Genre(val id: Int, val name: String)

data class UpcomingMoviesResponse(
    val page: Long,
    val results: List<Movie>,
    @Json(name = "total_pages") val totalPages: Long,
    @Json(name = "total_results") val totalResults: Long
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String?,
    var genres: String,
    var popularity: String?,
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    @Json(name = "poster_path") var posterPath: String?,
    @Json(name = "original_language") var originalLanguage: String?,
    @Json(name = "vote_average") var voteAverage: String?,
    @Json(name = "backdrop_path") var backdropPath: String?,
    @Json(name = "release_date") val releaseDate: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        null,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(overview)
        parcel.writeString(genres)
        parcel.writeString(popularity)
        parcel.writeString(posterPath)
        parcel.writeString(originalLanguage)
        parcel.writeString(voteAverage)
        parcel.writeString(backdropPath)
        parcel.writeString(releaseDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}


