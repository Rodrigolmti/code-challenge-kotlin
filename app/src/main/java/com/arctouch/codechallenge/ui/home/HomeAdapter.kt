package com.arctouch.codechallenge.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.data.model.Movie
import com.arctouch.codechallenge.ui.base.BaseAdapter
import com.arctouch.codechallenge.ui.base.BaseViewHolder
import com.arctouch.codechallenge.ui.base.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

typealias LoadMoreItems = () -> Unit

const val PAGE_SIZE = 20

class HomeMovieAdapterPageHandler(
    private val layoutManager: GridLayoutManager,
    private val loadMoreItems: LoadMoreItems
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount

        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
            firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE
        ) {
            loadMoreItems()
        }
    }
}

class HomeMovieAdapter(private val onClick: OnClick<Movie>) : BaseAdapter<Movie>() {

    var homeMovieAdapterPageHandler: HomeMovieAdapterPageHandler? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Item(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        homeMovieAdapterPageHandler = null
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = (holder as Item)
        item.bindData(data[position])
        item.onClick(onClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun configAdapterPageHandler(
        layoutManager: GridLayoutManager,
        loadMoreItems: LoadMoreItems
    ): HomeMovieAdapterPageHandler? {
        homeMovieAdapterPageHandler = HomeMovieAdapterPageHandler(layoutManager, loadMoreItems)
        return homeMovieAdapterPageHandler
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