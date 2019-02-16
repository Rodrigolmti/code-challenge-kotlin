package com.arctouch.codechallenge.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

typealias OnClick<M> = (item: M) -> Unit

abstract class BaseAdapter<M> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var data: MutableList<M> = mutableListOf()

    fun addAll(list: List<M>) {
        this.data.addAll(list)
    }
}

abstract class BaseViewHolder<M>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onClick(onClick: OnClick<M>)
}