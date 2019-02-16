package com.arctouch.codechallenge.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

typealias OnClick<M> = (item: M) -> Unit

abstract class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()

abstract class BaseViewHolder<M>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onClick(onClick: OnClick<M>)
}