package com.scan.weather.weather.view.adapter

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageBitmap")
fun loadFileImage(
    imageView: ImageView,
    imageBitmap: Bitmap,
) {
    Glide.with(imageView).load(imageBitmap).into(imageView)
}