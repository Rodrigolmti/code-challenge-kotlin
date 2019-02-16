package com.arctouch.codechallenge.util

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun <VH : RecyclerView.ViewHolder> RecyclerView.setUpWithLinearLayout(adapter: RecyclerView.Adapter<VH>) {
    this.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
    this.layoutManager = LinearLayoutManager(this.context)
    this.adapter = adapter
}