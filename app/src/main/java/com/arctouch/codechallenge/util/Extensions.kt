package com.arctouch.codechallenge.util

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun <VH : RecyclerView.ViewHolder> RecyclerView.setUpWithGridView(adapter: RecyclerView.Adapter<VH>) {
    this.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
    this.layoutManager = GridLayoutManager(this.context, 3)
    this.adapter = adapter
}
