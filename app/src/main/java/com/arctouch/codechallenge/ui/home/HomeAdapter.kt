package com.arctouch.codechallenge.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.data.model.Movie
import com.arctouch.codechallenge.ui.base.BaseAdapter
import com.arctouch.codechallenge.ui.base.BaseViewHolder
import com.arctouch.codechallenge.ui.base.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

class HomeMovieAdapter(movies: List<Movie>, private val onClick: OnClick<Movie>) : BaseAdapter<Movie>() {

    init {
        addAll(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Item(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val item = (holder as Item)
        item.bindData(data[position])
        item.onClick(onClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class Item(itemView: View) : BaseViewHolder<Movie>(itemView) {

        private lateinit var item: Movie

        fun bindData(item: Movie) {

            itemView.textViewTitle.text = item.title

            Glide.with(itemView)
                .load(item.posterPath)
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.imageViewPoster)

            this.item = item
        }

        override fun onClick(onClick: OnClick<Movie>) {
            itemView.setOnClickListener { onClick(item) }
        }
    }
}