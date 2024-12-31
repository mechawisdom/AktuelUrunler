package com.mechadev.indirim.aktuel.urunler.util.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mechadev.indirim.aktuel.urunler.R
import com.mechadev.indirim.aktuel.urunler.util.photoview.PhotoView


val options = RequestOptions()
    .skipMemoryCache(false)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .placeholder(R.drawable.ic_placeholder_full)
    .error(R.drawable.ic_placeholder_full)


fun ImageView.loadImage(path: String) {
    Glide.with(this.context).load(path)
        .apply(options)
        .into(this)
}

fun PhotoView.loadPhotoView(path: String) {
    Glide.with(this.context).load(path)
        .apply(options)
        .into(this)
}