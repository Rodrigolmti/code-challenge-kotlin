package com.arctouch.codechallenge.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.arctouch.codechallenge.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("android:visibility")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context)
            .load(it)
            .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
            .into(imageView)
    }
}